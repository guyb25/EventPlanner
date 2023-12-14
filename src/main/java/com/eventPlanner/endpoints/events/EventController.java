package com.eventPlanner.endpoints.events;

import com.eventPlanner.models.dtos.events.EventCreationDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
@Tag(name = "Event Management")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createEvent(EventCreationDto eventCreationDto) {
        return this.eventService
                .CreateEvent(
                        eventCreationDto.name(),
                        eventCreationDto.description(),
                        eventCreationDto.location(),
                        eventCreationDto.time(),
                        eventCreationDto.participants()
                )
                .toResponse();
    }
}
