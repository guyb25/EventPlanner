package com.eventPlanner.endpoints.events.eventService;

import com.eventPlanner.dummyBuilders.RandomValueGenerator;
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
        var userId = RandomValueGenerator.randomUniqueLong();
        var username = RandomValueGenerator.randomUniqueString();

        var eventDummy1 = eventDummyBuilder.generate().build();
        var eventDummy2 = eventDummyBuilder.generate().build();

        var eventDtoDummy1 = eventDtoDummyBuilder.fromEvent(eventDummy1).withParticipants(List.of(username)).build();
        var eventDtoDummy2 = eventDtoDummyBuilder.fromEvent(eventDummy2).withParticipants(List.of(username)).build();

        var eventListDummy = List.of(eventDummy1, eventDummy2);
        var eventDtoListDummy = List.of(eventDtoDummy1, eventDtoDummy2);

        var expectedResponse = responseProvider.event().eventDataList(eventDtoListDummy);

        when(sessionManager.getUserIdFromSession(dto.sessionId())).thenReturn(userId);

        for (int i = 0; i < eventListDummy.size(); i++) {
            var eventDummy = eventListDummy.get(i);
            var eventDtoDummy = eventDtoListDummy.get(i);
            when(userDataService.tryGetUsernameById(eventDummy.getHostId())).thenReturn(eventDtoDummy.host());
            when(participantDataService.findEventParticipantsNames(eventDummy.getId())).thenReturn(eventDtoDummy.participants());
        }

        when(eventDataService.findUserEventsSorted(userId, EventSortMethod.DATE)).thenReturn(eventListDummy);
        when(participantDataService.findEventParticipantsNames(eventDummy1.getId())).thenReturn(eventDtoDummy2.participants());

        // Act
        var actualResponse = eventService.getAuthorizedEvents(dto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}
