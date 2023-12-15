package com.eventPlanner.endpoints.events.eventService;

import com.eventPlanner.models.dtos.events.EventDataDto;
import com.eventPlanner.models.dtos.events.RequestOwnedEventsDto;
import com.eventPlanner.models.schemas.Event;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetOwnedEventsTest extends BaseEventServiceTest {
    private final RequestOwnedEventsDto requestOwnedEventsDto = new RequestOwnedEventsDto("sessionId");

    @Test
    public void testGetOwnedEvents_InvalidSession_ReturnFailure() {
        // Arrange
        var expectedResponse = responseProvider.session().invalidSession();
        when(sessionManager.missing(requestOwnedEventsDto.sessionId())).thenReturn(true);

        // Act
        var actualResponse = eventService.getOwnedEvents(requestOwnedEventsDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    public void testGetOwnedEvents_ValidSession_ReturnOwnedEvents() {
        // Arrange
        var userId = 50L;
        var eventDataDto = new EventDataDto(
                999L,
                "event",
                "host",
                "Description",
                "location",
                LocalDateTime.now(),
                LocalDateTime.now(),
                new ArrayList<>());

        var matchingEvent = new Event(
                eventDataDto.name(),
                userId,
                eventDataDto.description(),
                eventDataDto.location(),
                eventDataDto.time(),
                eventDataDto.creationTime()
        );

        matchingEvent.setId(eventDataDto.id());

        var eventDataList = List.of(eventDataDto);

        var expectedResponse = responseProvider.event().eventDataList(eventDataList);

        when(sessionManager.getUserIdFromSession(requestOwnedEventsDto.sessionId())).thenReturn(userId);
        when(userDataService.tryGetUsernameById(userId)).thenReturn(eventDataDto.host());
        when(eventDataService.findEventsByHostId(userId)).thenReturn(List.of(matchingEvent));

        // Act
        var actualResponse = eventService.getOwnedEvents(requestOwnedEventsDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}
