package com.eventPlanner.endpoints.events;

import com.eventPlanner.dataAccess.sessions.SessionManager;
import com.eventPlanner.dataAccess.userEvents.EventRepository;
import com.eventPlanner.dataAccess.userEvents.ParticipantsRepository;
import com.eventPlanner.dataAccess.userEvents.UserRepository;
import com.eventPlanner.models.dtos.events.EventDataDto;
import com.eventPlanner.models.schemas.Event;
import com.eventPlanner.models.schemas.Participant;
import com.eventPlanner.models.serviceResponse.ServiceResponse;
import com.eventPlanner.models.serviceResponse.providers.ResponseProvider;
import com.eventPlanner.models.types.EventSortMethod;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final ParticipantsRepository participantsRepository;
    private final UserRepository userRepository;
    private final SessionManager sessionManager;
    private final ResponseProvider responseProvider;

    private final Map<EventSortMethod, Function<Long, List<Event>>> sortMethodFunctionMap = new HashMap<>();

    @Autowired
    public EventService(EventRepository eventRepository, ParticipantsRepository participantsRepository, UserRepository userRepository, SessionManager sessionManager, ResponseProvider responseProvider) {
        this.eventRepository = eventRepository;
        this.participantsRepository = participantsRepository;
        this.userRepository = userRepository;
        this.sessionManager = sessionManager;
        this.responseProvider = responseProvider;

        sortMethodFunctionMap.put(EventSortMethod.DATE, eventRepository::findAllEventsByParticipantIdOrderByTime);
        sortMethodFunctionMap.put(EventSortMethod.CREATION_TIME, eventRepository::findAllEventsByParticipantIdOrderByCreationTime);
        sortMethodFunctionMap.put(EventSortMethod.POPULARITY, eventRepository::findAllEventsByParticipantIdOrderByPopularity);
    }

    @Transactional
    public ServiceResponse createEvent(String name, String sessionId, String description, String location, LocalDateTime time, List<String> participants) {
        if (sessionManager.missing(sessionId)) {
            return responseProvider.session().invalidSession();
        }

        Long hostId = sessionManager.getUserIdFromSession(sessionId);
        Event event = new Event(name, hostId, description, location, time, LocalDateTime.now());

        for (String participant : participants) {
            if (!userRepository.existsUserByName(participant)) {
                throw new IllegalArgumentException("user not found: " + participant);
            }
        }

        // Make sure to grab the newly created event entity, that has the auto generated ID
        event = eventRepository.save(event);

        List<Participant> participantList = buildParticipantsList(participants, event.getId());
        participantsRepository.saveAll(participantList);

        return responseProvider.event().eventCreatedSuccessfully(event.getId());
    }

    @Transactional
    public ServiceResponse deleteEvent(Long id, String sessionId) {
        if (sessionManager.missing(sessionId)) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(sessionId);
        Event event = eventRepository.findEventById(id);

        if (event == null) {
            return responseProvider.event().eventNotFound(id);
        }

        if (!event.getHostId().equals(userId)) {
            return responseProvider.general().unauthorized();
        }

        eventRepository.deleteById(id);
        participantsRepository.deleteAllByEventId(id);
        return responseProvider.event().eventDeleted();
    }

    public ServiceResponse getOwnedEvents(String sessionId) {
        if (sessionManager.missing(sessionId)) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(sessionId);
        String host = userRepository.getReferenceById(userId).getName();
        List<Event> ownedEvents = eventRepository.findEventsByHostId(userId);
        List<EventDataDto> eventDataDtoList = ownedEvents.stream().map(event -> buildEventDataDto(event, host)).toList();

        return responseProvider.event().eventDataList(eventDataDtoList);
    }

    public ServiceResponse getAuthorizedEvents(String sessionId, EventSortMethod eventSortMethod) {
        if (sessionManager.missing(sessionId)) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(sessionId);
        String host = userRepository.getReferenceById(userId).getName();

        List<Event> events = sortMethodFunctionMap
                .getOrDefault(eventSortMethod, eventRepository::findAllEventsByParticipantId)
                .apply(userId);

        List<EventDataDto> eventDataDtoList = events.stream().map(event -> buildEventDataDto(event, host)).toList();

        return responseProvider.event().eventDataList(eventDataDtoList);
    }

    public ServiceResponse getSpecificEvent(String sessionId, Long eventId) {
        if (sessionManager.missing(sessionId)) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(sessionId);
        Event event = eventRepository.findEventById(eventId);
        List<Long> participants = participantsRepository.findAllByEventId(eventId);

        if (!event.getHostId().equals(userId) && !participants.contains(userId)) {
            return responseProvider.general().unauthorized();
        }

        String host = userRepository.getReferenceById(userId).getName();
        return responseProvider.event().eventData(buildEventDataDto(event, host));
    }

    @Transactional
    public ServiceResponse updateSpecificEvent(String sessionId, Long eventId, String name, String description,
                                               String location, LocalDateTime time, List<String> participants) {
        if (sessionManager.missing(sessionId)) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(sessionId);
        Event event = eventRepository.findEventById(eventId);

        if (!event.getHostId().equals(userId)) {
            return responseProvider.general().unauthorized();
        }

        updateIfNotNull(event::setName, name);
        updateIfNotNull(event::setDescription, description);
        updateIfNotNull(event::setLocation, location);
        updateIfNotNull(event::setTime, time);

        if (participants != null) {
            participantsRepository.deleteAllByEventId(eventId);
            List<Participant> participantList = buildParticipantsList(participants, eventId);
            participantsRepository.saveAll(participantList);
        }

        return responseProvider.general().success();
    }

    public ServiceResponse getLocationEvents(String sessionId, String location) {
        if (sessionManager.missing(sessionId)) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(sessionId);
        String host = userRepository.getReferenceById(userId).getName();
        List<Event> events = eventRepository.findAllEventsByUserIdAndLocation(userId, location);
        List<EventDataDto> eventDataDtoList = events.stream().map(event -> buildEventDataDto(event, host)).toList();
        return responseProvider.event().eventDataList(eventDataDtoList);
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

    private List<Participant> buildParticipantsList(List<String> users, Long eventId) {
        return users
                .stream()
                .map(user -> new Participant(eventId, userRepository.findUserByName(user).getId()))
                .toList();
    }

    private <T> void updateIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}