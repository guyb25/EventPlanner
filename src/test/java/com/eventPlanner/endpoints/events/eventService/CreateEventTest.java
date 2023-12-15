package com.eventPlanner.endpoints.events.eventService;

import com.eventPlanner.models.dtos.events.CreateEventDto;
import com.eventPlanner.models.schemas.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
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
        eventDummy = new Event(
                "eventDummy",
                543L,
                "description",
                "location",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    public void testCreateEvent_InvalidSession_EventNotCreatedAndReturnFailure() {
        // Arrange
        var expectedResponse = responseProvider.session().invalidSession();

        // Act
        var actualResponse = eventService.createEvent(new CreateEventDto(
                eventDummy.getName(),
                invalidSessionId,
                eventDummy.getDescription(),
                eventDummy.getLocation(),
                eventDummy.getTime(),
                participantNamesDummy
        ));

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

        when(sessionManager.getUserIdFromSession(validSessionId)).thenReturn(eventDummy.getHostId());
        when(userDataService.doAllParticipantsExistByNames(participantNamesDummy)).thenReturn(true);
        when(eventDataService.scheduleEvent(any())).thenReturn(eventDummy);

        // Act
        var actualResponse = eventService.createEvent(new CreateEventDto(
                eventDummy.getName(),
                validSessionId,
                eventDummy.getDescription(),
                eventDummy.getLocation(),
                eventDummy.getTime(),
                participantNamesDummy
        ));

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
        when(sessionManager.getUserIdFromSession(validSessionId)).thenReturn(eventDummy.getHostId());
        when(userDataService.doAllParticipantsExistByNames(participantNamesDummy)).thenReturn(false);

        // Act
        var actualResponse = eventService.createEvent(new CreateEventDto(
                eventDummy.getName(),
                validSessionId,
                eventDummy.getDescription(),
                eventDummy.getLocation(),
                eventDummy.getTime(),
                participantNamesDummy
        ));

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(eventDataService, never()).scheduleEvent(any());
        verify(participantDataService, never()).inviteParticipantsToEvent(any(), any());
    }
}
