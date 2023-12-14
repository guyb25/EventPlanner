package com.eventPlanner.endpoints.events;

import com.eventPlanner.dataAccess.sessions.SessionManager;
import com.eventPlanner.dataAccess.userEvents.EventRepository;
import com.eventPlanner.dataAccess.userEvents.ParticipantsRepository;
import com.eventPlanner.dataAccess.userEvents.UserRepository;
import com.eventPlanner.models.dtos.events.EventDataDto;
import com.eventPlanner.models.schemas.Event;
import com.eventPlanner.models.schemas.Participant;
import com.eventPlanner.models.serviceResult.ServiceResult;
import com.eventPlanner.models.serviceResult.ServiceResultFactory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final ParticipantsRepository participantsRepository;
    private final UserRepository userRepository;
    private final SessionManager sessionManager;

    @Autowired
    public EventService(EventRepository eventRepository, ParticipantsRepository participantsRepository, UserRepository userRepository, SessionManager sessionManager) {
        this.eventRepository = eventRepository;
        this.participantsRepository = participantsRepository;
        this.userRepository = userRepository;
        this.sessionManager = sessionManager;
    }

    @Transactional
    public ServiceResult createEvent(String name, String sessionId, String description, String location, LocalDateTime time, List<String> participants) {
        Long hostId = sessionManager.getUserIdFromSession(sessionId);
        Event event = new Event(name, hostId, description, location, time, LocalDateTime.now());

        for (String participant : participants) {
            if (!userRepository.existsUserByName(participant)) {
                throw new IllegalArgumentException("user not found: " + participant);
            }
        }

        // Make sure to grab the newly created event entity, that has the auto generated ID
        event = eventRepository.save(event);

        for (String participant : participants) {
            Long participantId = userRepository.findUserByName(participant).getId();
            Participant eventParticipant = new Participant(event.getId(), participantId);
            participantsRepository.save(eventParticipant);
        }

        return ServiceResultFactory.eventCreatedSuccessfully(event.getId());
    }

    @Transactional
    public ServiceResult deleteEvent(Long id, String sessionId) {
        if (sessionManager.missing(sessionId)) {
            return ServiceResultFactory.invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(sessionId);
        Event event = eventRepository.findEventById(id);

        if (event == null) {
            return ServiceResultFactory.eventNotFound(id);
        }

        if (!event.getHostId().equals(userId)) {
            return ServiceResultFactory.unauthorized();
        }

        eventRepository.deleteById(id);
        participantsRepository.deleteAllByEventId(id);
        return ServiceResultFactory.eventDeleted();
    }

    public ServiceResult getOwnedEvents(String sessionId) {
        if (sessionManager.missing(sessionId)) {
            return ServiceResultFactory.invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(sessionId);
        String host = userRepository.getReferenceById(userId).getName();
        List<Event> ownedEvents = eventRepository.findEventsByHostId(userId);
        List<EventDataDto> eventDataDtoList = new ArrayList<>();

        for (Event event : ownedEvents) {
            eventDataDtoList.add(buildEventDataDto(event, host));
        }

        return ServiceResultFactory.eventDataList(eventDataDtoList);
    }

    public ServiceResult getAuthorizedEvents(String sessionId) {
        if (sessionManager.missing(sessionId)) {
            return ServiceResultFactory.invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(sessionId);
        String host = userRepository.getReferenceById(userId).getName();
        List<Long> eventIds = participantsRepository.findAllByUserId(userId);
        List<EventDataDto> eventDataDtoList = new ArrayList<>();

        for (Long eventId : eventIds) {
            Event event = eventRepository.findEventById(eventId);
            eventDataDtoList.add(buildEventDataDto(event, host));
        }

        return ServiceResultFactory.eventDataList(eventDataDtoList);
    }

    public ServiceResult getSpecificEvent(String sessionId, Long eventId) {
        if (sessionManager.missing(sessionId)) {
            return ServiceResultFactory.invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(sessionId);
        Event event = eventRepository.findEventById(eventId);
        List<Long> participants = participantsRepository.findAllByEventId(eventId);

        if (!event.getHostId().equals(userId) && !participants.contains(userId)) {
            return ServiceResultFactory.unauthorized();
        }

        String host = userRepository.getReferenceById(userId).getName();
        return ServiceResultFactory.eventData(buildEventDataDto(event, host));
    }

    private EventDataDto buildEventDataDto(Event event, String host) {
        List<Long> participantsIds = participantsRepository.findAllByEventId(event.getId());
        List<String> participantsNames = new ArrayList<>();

        for (Long participantId : participantsIds) {
            participantsNames.add(userRepository.findUserById(participantId).getName());
        }

        return (new EventDataDto(event.getId(), event.getName(), host, event.getDescription(),
                event.getLocation(), event.getTime(), event.getCreationTime(), participantsNames));
    }
}
