package com.eventPlanner.endpoints.events.eventService;

import com.eventPlanner.dataAccess.userEvents.EventRepository;
import com.eventPlanner.endpoints.BaseServiceTest;
import com.eventPlanner.endpoints.events.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class BaseEventServiceTest extends BaseServiceTest {
    @Mock
    protected EventRepository eventRepository;
    @InjectMocks
    protected EventService eventService;

    protected final String invalidSessionId = "invalidSessionId";
    protected final String validSessionId = "validSessionId";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        eventService = new EventService(eventRepository, participantsRepository, userRepository, sessionManager, responseProvider);

        when(sessionManager.missing(invalidSessionId)).thenReturn(true);
        when(sessionManager.missing(validSessionId)).thenReturn(false);
    }
}
