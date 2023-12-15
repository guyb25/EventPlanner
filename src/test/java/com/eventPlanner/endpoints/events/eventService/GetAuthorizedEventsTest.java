package com.eventPlanner.endpoints.events.eventService;

import com.eventPlanner.models.dtos.events.RequestAuthorizedEventsDto;
import org.junit.jupiter.api.Test;

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
    public void testGetAuthorizedEvents_Unsorted_ReturnUnsortedEvents() {
        // Arrange
        var dto = new RequestAuthorizedEventsDto("sessionId", null);
        //var expectedResponse = responseProvider.event().eventDataList()
    }
}
