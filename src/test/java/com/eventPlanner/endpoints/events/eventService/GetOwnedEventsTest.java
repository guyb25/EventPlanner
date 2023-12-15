package com.eventPlanner.endpoints.events.eventService;

import com.eventPlanner.testUtils.UniqueValueGenerator;
import com.eventPlanner.models.dtos.events.RequestOwnedEventsDto;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetOwnedEventsTest extends BaseEventServiceTest {
    @Test
    public void testGetOwnedEvents_InvalidSession_ReturnFailure() {
        // Arrange
        var requestOwnedEventsDto = new RequestOwnedEventsDto(UniqueValueGenerator.uniqueString());
        var expectedResponse = responseProvider.session().invalidSession();
        mockInvalidSession(requestOwnedEventsDto.sessionId());

        // Act
        var actualResponse = eventService.getOwnedEvents(requestOwnedEventsDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    public void testGetOwnedEvents_ValidSession_ReturnOwnedEvents() {
        // Arrange
        var requestOwnedEventsDto = new RequestOwnedEventsDto(UniqueValueGenerator.uniqueString());
        var userId = UniqueValueGenerator.uniqueLong();
        var eventDummy = eventDummyBuilder.generate().withHostId(userId).build();
        var eventDataDto = eventDtoDummyBuilder.fromEvent(eventDummy).withHost(UniqueValueGenerator.uniqueString()).build();
        var eventDataList = List.of(eventDataDto);

        var expectedResponse = responseProvider.event().eventDataList(eventDataList);

        mockValidSession(requestOwnedEventsDto.sessionId());
        when(sessionManager.getUserIdFromSession(requestOwnedEventsDto.sessionId())).thenReturn(userId);
        when(eventDataService.findEventsByHostId(userId)).thenReturn(List.of(eventDummy));
        mockEventParticipantsAndHostUsernames(eventDummy, eventDataDto);

        // Act
        var actualResponse = eventService.getOwnedEvents(requestOwnedEventsDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}
