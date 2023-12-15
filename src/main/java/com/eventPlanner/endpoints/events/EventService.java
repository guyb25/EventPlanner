package com.eventPlanner.endpoints.events;

import com.eventPlanner.dataAccess.sessions.SessionManager;
import com.eventPlanner.dataAccess.userEvents.services.EventDataService;
import com.eventPlanner.dataAccess.userEvents.services.ParticipantDataService;
import com.eventPlanner.dataAccess.userEvents.services.UserDataService;
import com.eventPlanner.models.dtos.events.EventDataDto;
import com.eventPlanner.models.schemas.Event;
import com.eventPlanner.models.serviceResponse.ServiceResponse;
import com.eventPlanner.models.serviceResponse.providers.ResponseProvider;
import com.eventPlanner.models.types.EventSortMethod;
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
    public EventService(SessionManager sessionManager, ResponseProvider responseProvider, UserDataService userDataService,
                        ParticipantDataService participantDataService, EventDataService eventDataService) {
        this.sessionManager = sessionManager;
        this.responseProvider = responseProvider;
        this.userDataService = userDataService;
        this.participantDataService = participantDataService;
        this.eventDataService = eventDataService;
    }

    @Transactional
    public ServiceResponse createEvent(String name, String sessionId, String description,
                                       String location, LocalDateTime time, List<String> participants) {
        if (sessionManager.missing(sessionId)) {
            return responseProvider.session().invalidSession();
        }

        Long hostId = sessionManager.getUserIdFromSession(sessionId);

        if (!userDataService.doAllParticipantsExistByNames(participants)) {
            throw new IllegalArgumentException("invalid participants list");
        }

        var event = eventDataService.scheduleEvent(new Event(name, hostId, description, location, time, LocalDateTime.now()));
        participantDataService.inviteParticipantsToEvent(event.getId(), participants);

        return responseProvider.event().eventCreated(event.getId());
    }

    @Transactional
    public ServiceResponse deleteEvent(Long eventId, String sessionId) {
        if (sessionManager.missing(sessionId)) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(sessionId);
        Event event = eventDataService.tryFindEventById(eventId);

        if (event == null) {
            return responseProvider.event().eventNotFound(eventId);
        }

        if (!event.getHostId().equals(userId)) {
            return responseProvider.general().unauthorized();
        }

        eventDataService.deleteEventById(eventId);
        participantDataService.deleteAllEventParticipants(eventId);
        return responseProvider.event().eventDeleted();
    }

    public ServiceResponse getOwnedEvents(String sessionId) {
        if (sessionManager.missing(sessionId)) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(sessionId);
        String host = userDataService.tryGetUsernameById(userId);
        List<Event> ownedEvents = eventDataService.findEventsByHostId(userId);
        List<EventDataDto> eventDataDtoList = ownedEvents
                .stream()
                .map(event -> buildEventDataDto(event, host))
                .toList();

        return responseProvider.event().eventDataList(eventDataDtoList);
    }

    public ServiceResponse getAuthorizedEvents(String sessionId, EventSortMethod eventSortMethod) {
        if (sessionManager.missing(sessionId)) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(sessionId);
        String host = userDataService.tryGetUsernameById(userId);

        var events = eventDataService.findUserEventsSorted(userId, eventSortMethod);
        List<EventDataDto> eventDataDtoList = events.stream().map(event -> buildEventDataDto(event, host)).toList();

        return responseProvider.event().eventDataList(eventDataDtoList);
    }

    public ServiceResponse getSpecificEvent(String sessionId, Long eventId) {
        if (sessionManager.missing(sessionId)) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(sessionId);
        Long hostId = eventDataService.tryFindEventHostId(eventId);
        Event event = eventDataService.tryFindEventById(eventId);
        List<Long> participantIds = participantDataService.findEventParticipantsIds(eventId);

        if (event == null) {
            return responseProvider.event().eventNotFound(eventId);
        }

        if (!hostId.equals(userId) && !participantIds.contains(userId)) {
            return responseProvider.general().unauthorized();
        }

        String host = userDataService.tryGetUsernameById(hostId);
        return responseProvider.event().eventData(buildEventDataDto(event, host));
    }

    @Transactional
    public ServiceResponse updateSpecificEvent(String sessionId, Long eventId, String name, String description,
                                               String location, LocalDateTime time, List<String> participants) {
        if (sessionManager.missing(sessionId)) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(sessionId);
        Event event = eventDataService.tryFindEventById(eventId);

        if (!event.getHostId().equals(userId)) {
            return responseProvider.general().unauthorized();
        }

        updateIfNotNull(event::setName, name);
        updateIfNotNull(event::setDescription, description);
        updateIfNotNull(event::setLocation, location);
        updateIfNotNull(event::setTime, time);

        if (participants != null) {
            participantDataService.deleteAllEventParticipants(eventId);
            participantDataService.inviteParticipantsToEvent(eventId, participants);
        }

        return responseProvider.general().success();
    }

    public ServiceResponse getLocationEvents(String sessionId, String location) {
        if (sessionManager.missing(sessionId)) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(sessionId);
        String host = userDataService.tryGetUsernameById(userId);
        List<Event> events = eventDataService.findAllEventsByUserIdAndLocation(userId, location);
        List<EventDataDto> eventDataDtoList = events
                .stream()
                .map(event -> buildEventDataDto(event, host))
                .toList();

        return responseProvider.event().eventDataList(eventDataDtoList);
    }

    private EventDataDto buildEventDataDto(Event event, String host) {
        var participantsNames = participantDataService.findEventParticipantsNames(event.getId());

        return (new EventDataDto(event.getId(), event.getName(), host, event.getDescription(),
                event.getLocation(), event.getTime(), event.getCreationTime(), participantsNames));
    }

    private <T> void updateIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}