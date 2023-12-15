package com.eventPlanner.endpoints.events.eventService;

import com.eventPlanner.dataAccess.userEvents.services.EventDataService;
import com.eventPlanner.dummyBuilders.EventDtoDummyBuilder;
import com.eventPlanner.endpoints.BaseServiceTest;
import com.eventPlanner.dummyBuilders.EventDummyBuilder;
import com.eventPlanner.endpoints.events.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class BaseEventServiceTest extends BaseServiceTest {
    @Mock
    protected EventDataService eventDataService;
    @InjectMocks
    protected EventService eventService;
    protected EventDummyBuilder eventDummyBuilder;
    protected EventDtoDummyBuilder eventDtoDummyBuilder;

    protected final String invalidSessionId = "invalidSessionId";
    protected final String validSessionId = "validSessionId";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        eventService = new EventService(sessionManager, responseProvider,
                userDataService, participantDataService, eventDataService);
        eventDummyBuilder = new EventDummyBuilder();
        eventDtoDummyBuilder = new EventDtoDummyBuilder();

        when(sessionManager.missing(invalidSessionId)).thenReturn(true);
        when(sessionManager.missing(validSessionId)).thenReturn(false);
    }
}
