package com.eventPlanner.endpoints.events.eventService;

import com.eventPlanner.models.schemas.Event;
import com.eventPlanner.models.schemas.Participant;
import com.eventPlanner.models.schemas.User;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class CreateEventTest extends BaseEventServiceTest {
    @Test
    public void testCreateEvent_InvalidSession_EventNotCreatedAndReturnFailure() {
        // Arrange
        var expectedResponse = responseProvider.session().invalidSession();

        // Act
        var actualResponse = eventService.createEvent("name", invalidSessionId, "description", "location",
                LocalDateTime.now(), new ArrayList<>());

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(eventRepository, never()).save(any());
        verify(participantsRepository, never()).save(any());
    }

    @Test
    public void testCreateEvent_AllUsersExist_EventCreatedAndReturnSuccess() {
        // Arrange
        var eventId = 123L;
        var hostId = 543L;
        var expectedResponse = responseProvider.event().eventCreated(eventId);

        var userStubs = List.of(
                new User("user1", "1", "1", 675L),
                new User("user2", "2", "2", 231L),
                new User("user3", "3", "3", 991L)
        );

        var participants = userStubs.stream().map(user -> new Participant(eventId, user.getId())).toList();
        var participantNames = userStubs.stream().map(User::getName).toList();

        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        ArgumentCaptor<List<Participant>> participantsCaptor = ArgumentCaptor.forClass(List.class);

        when(sessionManager.getUserIdFromSession(validSessionId)).thenReturn(hostId);

        for (int i = 0; i < participants.size(); i++) {
            when(userRepository.existsUserByName(userStubs.get(i).getName())).thenReturn(true);
            when(userRepository.findUserByName(userStubs.get(i).getName())).thenReturn(userStubs.get(i));
        }

        when(eventRepository.save(eventCaptor.capture())).thenAnswer(invocation -> {
            Event eventArgument = invocation.getArgument(0); // Captured Event argument
            eventArgument.setId(eventId);
            return eventArgument; // Return the modified Event with the ID
        });

        // Act
        var actualResponse = eventService.createEvent(
                "name",
                validSessionId,
                "description",
                "location",
                LocalDateTime.now(),
                participantNames
        );

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(eventRepository, times(1)).save(eventCaptor.capture());
        verify(participantsRepository, times(1)).saveAll(participantsCaptor.capture());

        assertThat(eventCaptor.getValue().getHostId()).isEqualTo(hostId);
        assertThat(participantsCaptor.getValue()).usingRecursiveComparison().isEqualTo(participants);
    }

    @Test
    public void testCreateEvent_ParticipantsDontExist_EventNotCreatedAndReturnFailure() {
        // Arrange
        var hostId = 543L;
        var participantNames = List.of("user1", "user2", "user3");

        when(sessionManager.getUserIdFromSession(validSessionId)).thenReturn(hostId);

        for (var participantName : participantNames) {
            when(userRepository.existsUserByName(participantName)).thenReturn(false);
        }

        // Act
        assertThatThrownBy(
                () -> eventService.createEvent(
                        "user1",
                        validSessionId,
                        "description",
                        "location",
                        LocalDateTime.now(),
                        participantNames
                )
        ).isInstanceOf(IllegalArgumentException.class);

        // Assert
        verify(eventRepository, never()).save(any());
        verify(participantsRepository, never()).saveAll(any());
    }
}
