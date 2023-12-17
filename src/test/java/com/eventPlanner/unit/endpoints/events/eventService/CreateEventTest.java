package com.eventPlanner.unit.endpoints.events.eventService;

import com.eventPlanner.testUtils.UniqueValueGenerator;
import com.eventPlanner.core.models.dtos.events.CreateEventDto;
import com.eventPlanner.dataAccess.userEvents.schemas.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CreateEventTest extends BaseEventServiceTest {
    private Event eventDummy;
    private CreateEventDto createEventDto;

    @BeforeEach
    public void setup() {
        super.setup();
        eventDummy = eventDummyBuilder.generate().build();
        createEventDto = new CreateEventDto(
                eventDummy.getName(),
                UniqueValueGenerator.uniqueString(),
                eventDummy.getDescription(),
                eventDummy.getLocation(),
                eventDummy.getTime(),
                List.of(
                        UniqueValueGenerator.uniqueString(),
                        UniqueValueGenerator.uniqueString(),
                        UniqueValueGenerator.uniqueString()
                )
        );
    }

    @Test
    public void testCreateEvent_InvalidSession_EventNotCreatedAndReturnFailure() {
        // Arrange
        var expectedResponse = responseProvider.session().invalidSession();
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
        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);

        mockValidSession(createEventDto.sessionId());
        when(sessionManager.getUserIdFromSession(createEventDto.sessionId())).thenReturn(eventDummy.getHostId());
        when(userDataService.doAllParticipantsExistByNames(createEventDto.participants())).thenReturn(true);
        when(eventDataService.scheduleEvent(any())).thenReturn(eventDummy);

        // Act
        var actualResponse = eventService.createEvent(createEventDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);

        verify(eventDataService, times(1))
                .scheduleEvent(eventCaptor.capture());

        verify(participantDataService, times(1))
                .inviteParticipantsToEvent(eventDummy.getId(), createEventDto.participants());

        assertThat(eventCaptor.getValue().getHostId()).isEqualTo(eventDummy.getHostId());
    }

    @Test
    public void testCreateEvent_ParticipantsDontExist_EventNotCreatedAndExceptionThrown() {
        // Arrange
        var expectedResponse = responseProvider.event().participantsNotExist();

        mockValidSession(createEventDto.sessionId());
        when(sessionManager.getUserIdFromSession(createEventDto.sessionId())).thenReturn(eventDummy.getHostId());
        when(userDataService.doAllParticipantsExistByNames(createEventDto.participants())).thenReturn(false);

        // Act
        var actualResponse = eventService.createEvent(createEventDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(eventDataService, never()).scheduleEvent(any());
        verify(participantDataService, never()).inviteParticipantsToEvent(any(), any());
    }
}
