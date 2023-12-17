package com.eventPlanner.dataAccess.userEvents.services;

import com.eventPlanner.dataAccess.userEvents.repositories.EventRepository;
import com.eventPlanner.dataAccess.userEvents.schemas.Event;
import com.eventPlanner.core.models.dtos.events.EventSortMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class EventDataService {
    private final EventRepository eventRepository;
    private final Map<EventSortMethod, Function<Long, List<Event>>> sortMethodFunctionMap = new HashMap<>();

    @Autowired
    public EventDataService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;

        sortMethodFunctionMap.put(EventSortMethod.NONE, eventRepository::findAllEventsByParticipantId);
        sortMethodFunctionMap.put(EventSortMethod.DATE, eventRepository::findAllEventsByParticipantIdOrderByTime);
        sortMethodFunctionMap.put(EventSortMethod.CREATION_TIME, eventRepository::findAllEventsByParticipantIdOrderByCreationTime);
        sortMethodFunctionMap.put(EventSortMethod.POPULARITY, eventRepository::findAllEventsByParticipantIdOrderByPopularity);
    }

    public Event scheduleEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> findUserEventsSorted(Long userId, EventSortMethod eventSortMethod) {
        return sortMethodFunctionMap
                .getOrDefault(eventSortMethod, eventRepository::findAllEventsByParticipantId)
                .apply(userId);
    }

    public void deleteEventById(Long id) {
        eventRepository.deleteById(id);
    }

    public Event tryFindEventById(Long id) {
        return eventRepository.findEventById(id);
    }

    public List<Event> findEventsByHostId(Long hostId) {
        return eventRepository.findEventsByHostId(hostId);
    }

    public List<Event> findAllEventsByUserIdAndLocation(Long userId, String location) {
        return eventRepository.findAllEventsByUserIdAndLocation(userId, location);
    }

    public List<Event> findAllEventsThatOccurBefore(LocalDateTime time) {
        return eventRepository.findAllEventsThatWillOccurBefore(time);
    }
}
