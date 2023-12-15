package com.eventPlanner.endpoints.events.eventService;

import com.eventPlanner.dummyBuilders.RandomValueGenerator;
import com.eventPlanner.models.dtos.events.RequestOwnedEventsDto;
import org.junit.jupiter.api.Test;
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
        var userId = RandomValueGenerator.randomUniqueLong();
        var eventDummy = eventDummyBuilder.generate().withHostId(userId).build();
        var eventDataDto = eventDtoDummyBuilder.fromEvent(eventDummy).withHost(RandomValueGenerator.randomUniqueString()).build();
        var eventDataList = List.of(eventDataDto);

        var expectedResponse = responseProvider.event().eventDataList(eventDataList);

        when(sessionManager.getUserIdFromSession(requestOwnedEventsDto.sessionId())).thenReturn(userId);
        when(userDataService.tryGetUsernameById(userId)).thenReturn(eventDataDto.host());
        when(eventDataService.findEventsByHostId(userId)).thenReturn(List.of(eventDummy));
        when(participantDataService.findEventParticipantsNames(eventDummy.getId())).thenReturn(eventDataDto.participants());

        // Act
        var actualResponse = eventService.getOwnedEvents(requestOwnedEventsDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}
