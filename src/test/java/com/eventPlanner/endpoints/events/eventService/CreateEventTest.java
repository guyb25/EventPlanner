package com.eventPlanner.endpoints.events.eventService;

import com.eventPlanner.testUtils.UniqueValueGenerator;
import com.eventPlanner.models.dtos.events.CreateEventDto;
import com.eventPlanner.models.schemas.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CreateEventTest extends BaseEventServiceTest {
    private Event eventDummy;

    private List<String> participantNamesDummy;

    @BeforeEach
    public void setup() {
        super.setup();
        participantNamesDummy = List.of("user1", "user2", "user3");
        eventDummy = eventDummyBuilder.generate().build();
    }

    @Test
    public void testCreateEvent_InvalidSession_EventNotCreatedAndReturnFailure() {
        // Arrange
        var expectedResponse = responseProvider.session().invalidSession();
        var createEventDto = new CreateEventDto(
                eventDummy.getName(),
                UniqueValueGenerator.uniqueString(),
                eventDummy.getDescription(),
                eventDummy.getLocation(),
                eventDummy.getTime(),
                participantNamesDummy
        );

        mockInvalidSession(createEventDto.sessionId());

        // Act
        var actualResponse = eventService.createEvent(createEventDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(eventDataService, never()).scheduleEvent(any());
        verify(participantDataService, never()).inviteParticipantsToEvent(any(), any());
    }

    @Test
    public void testCreateEvent_AllUsersExist_EventCreatedAndReturnSuccess() {
        // Arrange
        var expectedResponse = responseProvider.event().eventCreated(eventDummy.getId());
        var createEventDto = new CreateEventDto(
                eventDummy.getName(),
                UniqueValueGenerator.uniqueString(),
                eventDummy.getDescription(),
                eventDummy.getLocation(),
                eventDummy.getTime(),
                participantNamesDummy
        );
        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);

        mockValidSession(createEventDto.sessionId());
        when(sessionManager.getUserIdFromSession(createEventDto.sessionId())).thenReturn(eventDummy.getHostId());
        when(userDataService.doAllParticipantsExistByNames(participantNamesDummy)).thenReturn(true);
        when(eventDataService.scheduleEvent(any())).thenReturn(eventDummy);

        // Act
        var actualResponse = eventService.createEvent(createEventDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);

        verify(eventDataService, times(1))
                .scheduleEvent(eventCaptor.capture());

        verify(participantDataService, times(1))
                .inviteParticipantsToEvent(eventDummy.getId(), participantNamesDummy);

        assertThat(eventCaptor.getValue().getHostId()).isEqualTo(eventDummy.getHostId());
    }

    @Test
    public void testCreateEvent_ParticipantsDontExist_EventNotCreatedAndExceptionThrown() {
        // Arrange
        var expectedResponse = responseProvider.event().participantsNotExist();
        var createEventDto = new CreateEventDto(
                eventDummy.getName(),
                UniqueValueGenerator.uniqueString(),
                eventDummy.getDescription(),
                eventDummy.getLocation(),
                eventDummy.getTime(),
                participantNamesDummy
        );

        mockValidSession(createEventDto.sessionId());
        when(sessionManager.getUserIdFromSession(createEventDto.sessionId())).thenReturn(eventDummy.getHostId());
        when(userDataService.doAllParticipantsExistByNames(participantNamesDummy)).thenReturn(false);

        // Act
        var actualResponse = eventService.createEvent(createEventDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(eventDataService, never()).scheduleEvent(any());
        verify(participantDataService, never()).inviteParticipantsToEvent(any(), any());
    }
}
