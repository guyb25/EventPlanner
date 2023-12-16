package com.eventPlanner.unit.endpoints.events.eventService;

import com.eventPlanner.unit.testUtils.UniqueValueGenerator;
import com.eventPlanner.models.dtos.events.RequestLocationEventsDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetLocationEventsTest extends BaseEventServiceTest {
    @Test
    public void getLocationEvents_InvalidSession_ReturnInvalidSession() {
        // Arrange
        var dto = new RequestLocationEventsDto(UniqueValueGenerator.uniqueString(), UniqueValueGenerator.uniqueString());
        var expectedResponse = responseProvider.session().invalidSession();
        mockInvalidSession(dto.sessionId());

        // Act
        var actualResponse = eventService.getLocationEvents(dto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    public void getLocationEvents_ValidRequest_ReturnEventsInLocation() {
        // Arrange
        var dto = new RequestLocationEventsDto(UniqueValueGenerator.uniqueString(), UniqueValueGenerator.uniqueString());
        var userId = UniqueValueGenerator.uniqueLong();

        var eventList = List.of(
                eventDummyBuilder.generate().withLocation(dto.location()).build(),
                eventDummyBuilder.generate().withLocation(dto.location()).build()
        );

        var eventDtoList = List.of(
                eventDtoDummyBuilder.fromEvent(eventList.get(0)).build(),
                eventDtoDummyBuilder.fromEvent(eventList.get(1)).build()
        );

        var expectedResponse = responseProvider.event().eventDataList(eventDtoList);

        mockValidSession(dto.sessionId());
        when(sessionManager.getUserIdFromSession(dto.sessionId())).thenReturn(userId);
        when(eventDataService.findAllEventsByUserIdAndLocation(userId, dto.location())).thenReturn(eventList);
        mockEventParticipantsAndHostUsernames(eventList, eventDtoList);

        // Act
        var actualResponse = eventService.getLocationEvents(dto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}
