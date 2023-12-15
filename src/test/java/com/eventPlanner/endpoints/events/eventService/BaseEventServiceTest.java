package com.eventPlanner.endpoints.events.eventService;

import com.eventPlanner.dataAccess.userEvents.EventRepository;
import com.eventPlanner.endpoints.BaseServiceTest;
import com.eventPlanner.endpoints.events.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BaseEventServiceTest extends BaseServiceTest {
    @Mock
    protected EventRepository eventRepository;
    @InjectMocks
    protected EventService eventService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        eventService = new EventService(eventRepository, participantsRepository, userRepository, sessionManager, responseProvider);
    }
}
