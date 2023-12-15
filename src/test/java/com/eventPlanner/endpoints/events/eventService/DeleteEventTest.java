package com.eventPlanner.endpoints.events.eventService;

import com.eventPlanner.models.dtos.events.DeleteEventDto;
import com.eventPlanner.models.schemas.Event;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DeleteEventTest extends BaseEventServiceTest {
    private final DeleteEventDto deleteEventDto = new DeleteEventDto(999L, "sessionId");

    @Test
    public void testDeleteEvent_InvalidSession_EventNotDeletedAndReturnFailure() {
        // Arrange
        var expectedResponse = responseProvider.session().invalidSession();
        when(sessionManager.missing(deleteEventDto.sessionId())).thenReturn(true);

        // Act
        var actualResponse = eventService.deleteEvent(deleteEventDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    public void testDeleteEvent_EventNotFound_EventNotDeletedAndReturnFailure() {
        // Arrange
        var expectedResponse = responseProvider.event().eventNotFound(deleteEventDto.eventId());
        when(sessionManager.missing(deleteEventDto.sessionId())).thenReturn(false);
        when(eventDataService.tryFindEventById(deleteEventDto.eventId())).thenReturn(null);

        // Act
        var actualResponse = eventService.deleteEvent(deleteEventDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(eventDataService, never()).deleteEventById(any());
        verify(participantDataService, never()).deleteAllEventParticipants(any());
    }

    @Test
    public void testDeleteEvent_NotAuthorized_EventNotDeletedAndReturnFailure() {
        // Arrange
        var eventHostId = 1L;
        var userId = 2L;

        var expectedResponse = responseProvider.general().unauthorized();
        var eventDummy = eventDummyBuilder.generate().withHostId(eventHostId).build();

        when(sessionManager.missing(deleteEventDto.sessionId())).thenReturn(false);
        when(sessionManager.getUserIdFromSession(deleteEventDto.sessionId())).thenReturn(userId);
        when(eventDataService.tryFindEventById(deleteEventDto.eventId())).thenReturn(eventDummy);

        // Act
        var actualResponse = eventService.deleteEvent(deleteEventDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(eventDataService, never()).deleteEventById(any());
        verify(participantDataService, never()).deleteAllEventParticipants(any());
    }

    @Test
    public void testDeleteEvent_ValidRequest_EventDeletedAndReturnSuccess() {
        // Arrange
        var expectedResponse = responseProvider.event().eventDeleted();
        var eventDummy = eventDummyBuilder.generate().build();

        when(sessionManager.missing(deleteEventDto.sessionId())).thenReturn(false);
        when(sessionManager.getUserIdFromSession(deleteEventDto.sessionId())).thenReturn(eventDummy.getHostId());
        when(eventDataService.tryFindEventById(deleteEventDto.eventId())).thenReturn(eventDummy);

        // Act
        var actualResponse = eventService.deleteEvent(deleteEventDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(eventDataService, times(1)).deleteEventById(deleteEventDto.eventId());
        verify(participantDataService, times(1)).deleteAllEventParticipants(deleteEventDto.eventId());
    }
}
