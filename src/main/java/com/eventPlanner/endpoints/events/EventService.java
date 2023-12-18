package com.eventPlanner.endpoints.events;

import com.eventPlanner.core.models.dtos.events.*;
import com.eventPlanner.dataAccess.sessions.SessionManager;
import com.eventPlanner.dataAccess.userEvents.services.EventDataService;
import com.eventPlanner.dataAccess.userEvents.services.ParticipantDataService;
import com.eventPlanner.dataAccess.userEvents.services.UserDataService;
import com.eventPlanner.dataAccess.userEvents.schemas.Event;
import com.eventPlanner.core.models.responses.ServiceResponse;
import com.eventPlanner.core.models.responses.providers.ResponseProvider;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

@Service
public class EventService {
    private final SessionManager sessionManager;
    private final ResponseProvider responseProvider;
    private final UserDataService userDataService;
    private final ParticipantDataService participantDataService;
    private final EventDataService eventDataService;

    @Autowired
    public EventService(SessionManager sessionManager, ResponseProvider responseProvider,
                        UserDataService userDataService, ParticipantDataService participantDataService,
                        EventDataService eventDataService) {
        this.sessionManager = sessionManager;
        this.responseProvider = responseProvider;
        this.userDataService = userDataService;
        this.participantDataService = participantDataService;
        this.eventDataService = eventDataService;
    }

    @Transactional
    public ServiceResponse createEvent(CreateEventDto createEventDto) {
        if (sessionManager.missing(createEventDto.sessionId())) {
            return responseProvider.session().invalidSession();
        }

        Long hostId = sessionManager.getUserIdFromSession(createEventDto.sessionId());

        if (!userDataService.doAllParticipantsExistByNames(createEventDto.participants())) {
            return responseProvider.event().participantsNotExist();
        }

        var event = eventDataService.scheduleEvent(new Event(
                createEventDto.name(),
                hostId,
                createEventDto.description(),
                createEventDto.location(),
                createEventDto.time(),
                LocalDateTime.now()));

        participantDataService.inviteParticipantsToEvent(event.getId(), createEventDto.participants());

        return responseProvider.event().eventCreated(event.getId());
    }

    @Transactional
    public ServiceResponse deleteEvent(DeleteEventDto deleteEventDto) {
        if (sessionManager.missing(deleteEventDto.sessionId())) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(deleteEventDto.sessionId());
        Event event = eventDataService.tryFindEventById(deleteEventDto.eventId());

        if (event == null) {
            return responseProvider.event().eventNotFound(deleteEventDto.eventId());
        }

        if (!event.getHostId().equals(userId)) {
            return responseProvider.general().unauthorized();
        }

        eventDataService.deleteEventById(deleteEventDto.eventId());
        participantDataService.deleteAllEventParticipants(deleteEventDto.eventId());
        return responseProvider.event().eventDeleted();
    }

    public ServiceResponse getOwnedEvents(RequestOwnedEventsDto requestOwnedEventsDto) {
        if (sessionManager.missing(requestOwnedEventsDto.sessionId())) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(requestOwnedEventsDto.sessionId());
        List<Event> ownedEvents = eventDataService.findEventsByHostId(userId);
        List<EventDataDto> eventDataDtoList = ownedEvents
                .stream()
                .map(this::buildEventDataDto)
                .toList();

        return responseProvider.event().eventDataList(eventDataDtoList);
    }

    public ServiceResponse getAuthorizedEvents(RequestAuthorizedEventsDto requestAuthorizedEventsDto) {
        if (sessionManager.missing(requestAuthorizedEventsDto.sessionId())) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(requestAuthorizedEventsDto.sessionId());

        var events = eventDataService.findUserEventsSorted(userId, requestAuthorizedEventsDto.eventSortMethod());
        List<EventDataDto> eventDataDtoList = events.stream().map(this::buildEventDataDto).toList();

        return responseProvider.event().eventDataList(eventDataDtoList);
    }

    public ServiceResponse getSpecificEvent(RequestSpecificEventDto requestSpecificEventDto) {
        var sessionId = requestSpecificEventDto.sessionId();
        var eventId = requestSpecificEventDto.eventId();

        if (sessionManager.missing(sessionId)) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(sessionId);
        Event event = eventDataService.tryFindEventById(eventId);
        List<Long> participantIds = participantDataService.findEventParticipantsIds(eventId);

        if (event == null) {
            return responseProvider.event().eventNotFound(eventId);
        }

        if (!event.getHostId().equals(userId) && !participantIds.contains(userId)) {
            return responseProvider.general().unauthorized();
        }

        return responseProvider.event().eventData(buildEventDataDto(event));
    }

    @Transactional
    public ServiceResponse updateSpecificEvent(UpdateEventDto updateEventDto) {
        if (sessionManager.missing(updateEventDto.sessionId())) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(updateEventDto.sessionId());
        Event event = eventDataService.tryFindEventById(updateEventDto.eventId());

        if (event == null) {
            return responseProvider.event().eventNotFound(updateEventDto.eventId());
        }

        if (!event.getHostId().equals(userId)) {
            return responseProvider.general().unauthorized();
        }

        updateIfNotNull(event::setName, updateEventDto.name());
        updateIfNotNull(event::setDescription, updateEventDto.description());
        updateIfNotNull(event::setLocation, updateEventDto.location());
        updateIfNotNull(event::setTime, updateEventDto.time());

        if (updateEventDto.participants() != null) {
            participantDataService.deleteAllEventParticipants(updateEventDto.eventId());
            participantDataService.inviteParticipantsToEvent(updateEventDto.eventId(), updateEventDto.participants());
        }

        return responseProvider.general().success();
    }

    public ServiceResponse getLocationEvents(RequestLocationEventsDto requestLocationEventsDto) {
        if (sessionManager.missing(requestLocationEventsDto.sessionId())) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(requestLocationEventsDto.sessionId());
        List<Event> events = eventDataService.findAllEventsByUserIdAndLocation(userId, requestLocationEventsDto.location());
        List<EventDataDto> eventDataDtoList = events
                .stream()
                .map(this::buildEventDataDto)
                .toList();

        return responseProvider.event().eventDataList(eventDataDtoList);
    }

    private EventDataDto buildEventDataDto(Event event) {
        var participantsNames = participantDataService.findEventParticipantsNames(event.getId());
        var host = userDataService.tryGetUsernameById(event.getHostId());

        return (new EventDataDto(event.getId(), event.getName(), host, event.getDescription(),
                event.getLocation(), event.getTime(), event.getCreationTime(), participantsNames));
    }

    private <T> void updateIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}