package com.eventPlanner.endpoints.events;

import com.eventPlanner.models.dtos.events.EventCreationDto;
import com.eventPlanner.models.dtos.events.EventDeletionDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
@Tag(name = "Event Management")
public class EventController {
    private final EventServiceProxy eventServiceProxy;

    @Autowired
    public EventController(EventServiceProxy eventServiceProxy) {
        this.eventServiceProxy = eventServiceProxy;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createEvent(EventCreationDto eventCreationDto) {
        return eventServiceProxy.CreateEvent(eventCreationDto).toResponse();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEvent(EventDeletionDto eventDeletionDto) {
        return eventServiceProxy.DeleteEvent(eventDeletionDto).toResponse();
    }
}
