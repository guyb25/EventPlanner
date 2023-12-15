package com.eventPlanner.endpoints.events.eventService;

import com.eventPlanner.dummyBuilders.RandomValueGenerator;
import com.eventPlanner.models.dtos.events.RequestSpecificEventDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class GetSpecificEventTest extends BaseEventServiceTest {
    @Test
    public void testGetSpecificEvent_InvalidSession_ReturnFailure() {
        // Arrange
        var dto = new RequestSpecificEventDto(RandomValueGenerator.randomUniqueString(), RandomValueGenerator.randomUniqueLong());
        var expectedResponse = responseProvider.session().invalidSession();
        when(sessionManager.missing(dto.sessionId())).thenReturn(true);

        // Act
        var actualResponse = eventService.getSpecificEvent(dto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    public void testGetSpecificEvent_EventDoesntExist_ReturnFailure() {
        // Arrange
        var dto = new RequestSpecificEventDto(RandomValueGenerator.randomUniqueString(), RandomValueGenerator.randomUniqueLong());
        var expectedResponse = responseProvider.event().eventNotFound(dto.eventId());

        when(sessionManager.missing(dto.sessionId())).thenReturn(false);
        when(eventDataService.tryFindEventById(dto.eventId())).thenReturn(null);

        // Act
        var actualResponse = eventService.getSpecificEvent(dto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    public void testGetSpecificEvent_Unauthorized_ReturnUnauthorized() {
        // Arrange
        var dto = new RequestSpecificEventDto(RandomValueGenerator.randomUniqueString(), RandomValueGenerator.randomUniqueLong());
        var expectedResponse = responseProvider.general().unauthorized();

        var userId = RandomValueGenerator.randomUniqueLong();
        var differentUserId = RandomValueGenerator.randomUniqueLong();
        var eventDummy = eventDummyBuilder.generate().withHostId(differentUserId).build();

        when(sessionManager.missing(dto.sessionId())).thenReturn(false);
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
        var dto = new RequestSpecificEventDto(RandomValueGenerator.randomUniqueString(), RandomValueGenerator.randomUniqueLong());

        var userId = RandomValueGenerator.randomUniqueLong();
        var eventDummy = eventDummyBuilder.generate().withEventId(dto.eventId()).withHostId(userId).build();
        var eventDtoDummy = eventDtoDummyBuilder.fromEvent(eventDummy).build();
        var expectedResponse = responseProvider.event().eventData(eventDtoDummy);

        when(sessionManager.missing(dto.sessionId())).thenReturn(false);
        when(sessionManager.getUserIdFromSession(dto.sessionId())).thenReturn(userId);
        when(eventDataService.tryFindEventById(dto.eventId())).thenReturn(eventDummy);
        when(participantDataService.findEventParticipantsNames(dto.eventId())).thenReturn(eventDtoDummy.participants());
        when(userDataService.tryGetUsernameById(eventDummy.getHostId())).thenReturn(eventDtoDummy.host());

        // Act
        var actualResponse = eventService.getSpecificEvent(dto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    public void testGetSpecificEvent_AuthorizedEventParticipant_ReturnEvents() {
        // Arrange
        var dto = new RequestSpecificEventDto(RandomValueGenerator.randomUniqueString(), RandomValueGenerator.randomUniqueLong());

        var userId = RandomValueGenerator.randomUniqueLong();
        var username = RandomValueGenerator.randomUniqueString();
        var differentUserId = RandomValueGenerator.randomUniqueLong();
        var eventDummy = eventDummyBuilder.generate().withEventId(dto.eventId()).withHostId(differentUserId).build();
        var eventDtoDummy = eventDtoDummyBuilder.fromEvent(eventDummy).withParticipants(List.of(username)).build();
        var expectedResponse = responseProvider.event().eventData(eventDtoDummy);

        when(sessionManager.missing(dto.sessionId())).thenReturn(false);
        when(sessionManager.getUserIdFromSession(dto.sessionId())).thenReturn(userId);
        when(userDataService.tryGetUsernameById(userId)).thenReturn(username);
        when(eventDataService.tryFindEventById(dto.eventId())).thenReturn(eventDummy);
        when(participantDataService.findEventParticipantsIds(dto.eventId())).thenReturn(List.of(userId));
        when(participantDataService.findEventParticipantsNames(dto.eventId())).thenReturn(eventDtoDummy.participants());
        when(userDataService.tryGetUsernameById(eventDummy.getHostId())).thenReturn(eventDtoDummy.host());

        // Act
        var actualResponse = eventService.getSpecificEvent(dto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}
