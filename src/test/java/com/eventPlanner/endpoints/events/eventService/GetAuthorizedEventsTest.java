package com.eventPlanner.endpoints.events.eventService;

import com.eventPlanner.dummyBuilders.UniqueValueGenerator;
import com.eventPlanner.models.dtos.events.RequestAuthorizedEventsDto;
import com.eventPlanner.models.types.EventSortMethod;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetAuthorizedEventsTest extends BaseEventServiceTest {
    @Test
    public void testGetAuthorizedEvents_InvalidSession_ReturnInvalidSession() {
        // Arrange
        var dto = new RequestAuthorizedEventsDto("sessionId", null);
        var expectedResponse = responseProvider.session().invalidSession();

        when(sessionManager.missing(dto.sessionId())).thenReturn(true);

        // Act
        var actualResponse = eventService.getAuthorizedEvents(dto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    public void testGetAuthorizedEvents_Valid_ReturnUnsortedEvents() {
        // Arrange
        var dto = new RequestAuthorizedEventsDto("sessionId", EventSortMethod.DATE);
        var userId = UniqueValueGenerator.uniqueLong();
        var username = UniqueValueGenerator.uniqueString();

        var eventList = List.of(
                eventDummyBuilder.generate().build(),
                eventDummyBuilder.generate().build()
        );

        var eventDtoList = List.of(
                eventDtoDummyBuilder.fromEvent(eventList.get(0)).withParticipants(List.of(username)).build(),
                eventDtoDummyBuilder.fromEvent(eventList.get(1)).withParticipants(List.of(username)).build()
        );

        var expectedResponse = responseProvider.event().eventDataList(eventDtoList);

        mockEventParticipantsAndHostUsernames(eventList, eventDtoList);
        when(sessionManager.getUserIdFromSession(dto.sessionId())).thenReturn(userId);
        when(eventDataService.findUserEventsSorted(userId, EventSortMethod.DATE)).thenReturn(eventList);

        // Act
        var actualResponse = eventService.getAuthorizedEvents(dto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}
