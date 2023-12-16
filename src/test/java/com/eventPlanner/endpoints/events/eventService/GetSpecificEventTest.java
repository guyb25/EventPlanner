package com.eventPlanner.endpoints.events.eventService;

import com.eventPlanner.testUtils.UniqueValueGenerator;
import com.eventPlanner.models.dtos.events.RequestSpecificEventDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class GetSpecificEventTest extends BaseEventServiceTest {
    @Test
    public void testGetSpecificEvent_InvalidSession_ReturnFailure() {
        // Arrange
        var dto = new RequestSpecificEventDto(UniqueValueGenerator.uniqueString(), UniqueValueGenerator.uniqueLong());
        var expectedResponse = responseProvider.session().invalidSession();
        mockInvalidSession(dto.sessionId());

        // Act
        var actualResponse = eventService.getSpecificEvent(dto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    public void testGetSpecificEvent_EventDoesntExist_ReturnFailure() {
        // Arrange
        var dto = new RequestSpecificEventDto(UniqueValueGenerator.uniqueString(), UniqueValueGenerator.uniqueLong());
        var expectedResponse = responseProvider.event().eventNotFound(dto.eventId());

        mockValidSession(dto.sessionId());
        when(eventDataService.tryFindEventById(dto.eventId())).thenReturn(null);

        // Act
        var actualResponse = eventService.getSpecificEvent(dto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    public void testGetSpecificEvent_Unauthorized_ReturnUnauthorized() {
        // Arrange
        var dto = new RequestSpecificEventDto(UniqueValueGenerator.uniqueString(), UniqueValueGenerator.uniqueLong());
        var expectedResponse = responseProvider.general().unauthorized();

        var userId = UniqueValueGenerator.uniqueLong();
        var differentUserId = UniqueValueGenerator.uniqueLong();
        var eventDummy = eventDummyBuilder.generate().withHostId(differentUserId).build();

        mockValidSession(dto.sessionId());
        when(sessionManager.getUserIdFromSession(dto.sessionId())).thenReturn(userId);
        when(eventDataService.tryFindEventById(dto.eventId())).thenReturn(eventDummy);

        // Act
        var actualResponse = eventService.getSpecificEvent(dto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    public void testGetSpecificEvent_AuthorizedEventOwner_ReturnEvents() {
        // Arrange
        var dto = new RequestSpecificEventDto(UniqueValueGenerator.uniqueString(), UniqueValueGenerator.uniqueLong());

        var userId = UniqueValueGenerator.uniqueLong();
        var eventDummy = eventDummyBuilder.generate().withEventId(dto.eventId()).withHostId(userId).build();
        var eventDtoDummy = eventDtoDummyBuilder.fromEvent(eventDummy).build();
        var expectedResponse = responseProvider.event().eventData(eventDtoDummy);

        mockValidSession(dto.sessionId());
        when(sessionManager.getUserIdFromSession(dto.sessionId())).thenReturn(userId);
        when(eventDataService.tryFindEventById(dto.eventId())).thenReturn(eventDummy);
        mockEventParticipantsAndHostUsernames(eventDummy, eventDtoDummy);

        // Act
        var actualResponse = eventService.getSpecificEvent(dto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    public void testGetSpecificEvent_AuthorizedEventParticipant_ReturnEvents() {
        // Arrange
        var dto = new RequestSpecificEventDto(UniqueValueGenerator.uniqueString(), UniqueValueGenerator.uniqueLong());

        var userId = UniqueValueGenerator.uniqueLong();
        var username = UniqueValueGenerator.uniqueString();
        var differentUserId = UniqueValueGenerator.uniqueLong();
        var eventDummy = eventDummyBuilder.generate().withEventId(dto.eventId()).withHostId(differentUserId).build();
        var eventDtoDummy = eventDtoDummyBuilder.fromEvent(eventDummy).withParticipants(List.of(username)).build();
        var expectedResponse = responseProvider.event().eventData(eventDtoDummy);

        mockValidSession(dto.sessionId());
        when(sessionManager.getUserIdFromSession(dto.sessionId())).thenReturn(userId);
        when(userDataService.tryGetUsernameById(userId)).thenReturn(username);
        when(eventDataService.tryFindEventById(dto.eventId())).thenReturn(eventDummy);
        when(participantDataService.findEventParticipantsIds(dto.eventId())).thenReturn(List.of(userId));
        mockEventParticipantsAndHostUsernames(eventDummy, eventDtoDummy);

        // Act
        var actualResponse = eventService.getSpecificEvent(dto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}
