package com.eventPlanner.unit.endpoints.events.eventService;

import com.eventPlanner.unit.testUtils.UniqueValueGenerator;
import com.eventPlanner.models.dtos.events.DeleteEventDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DeleteEventTest extends BaseEventServiceTest {
    @Test
    public void testDeleteEvent_InvalidSession_EventNotDeletedAndReturnFailure() {
        // Arrange
        var deleteEventDto = new DeleteEventDto(UniqueValueGenerator.uniqueLong(), UniqueValueGenerator.uniqueString());
        var expectedResponse = responseProvider.session().invalidSession();
        mockInvalidSession(deleteEventDto.sessionId());

        // Act
        var actualResponse = eventService.deleteEvent(deleteEventDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    public void testDeleteEvent_EventNotFound_EventNotDeletedAndReturnFailure() {
        // Arrange
        var deleteEventDto = new DeleteEventDto(UniqueValueGenerator.uniqueLong(), UniqueValueGenerator.uniqueString());
        var expectedResponse = responseProvider.event().eventNotFound(deleteEventDto.eventId());

        mockValidSession(deleteEventDto.sessionId());
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
        var eventHostId = UniqueValueGenerator.uniqueLong();
        var userId = UniqueValueGenerator.uniqueLong();

        var deleteEventDto = new DeleteEventDto(UniqueValueGenerator.uniqueLong(), UniqueValueGenerator.uniqueString());
        var expectedResponse = responseProvider.general().unauthorized();
        var eventDummy = eventDummyBuilder.generate().withHostId(eventHostId).build();

        mockValidSession(deleteEventDto.sessionId());
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
        var deleteEventDto = new DeleteEventDto(UniqueValueGenerator.uniqueLong(), UniqueValueGenerator.uniqueString());
        var expectedResponse = responseProvider.event().eventDeleted();
        var eventDummy = eventDummyBuilder.generate().build();

        mockValidSession(deleteEventDto.sessionId());
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
