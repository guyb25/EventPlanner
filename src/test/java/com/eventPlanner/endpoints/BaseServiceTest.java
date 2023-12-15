package com.eventPlanner.endpoints;

import com.eventPlanner.dataAccess.sessions.SessionManager;
import com.eventPlanner.dataAccess.userEvents.ParticipantsRepository;
import com.eventPlanner.dataAccess.userEvents.UserRepository;
import com.eventPlanner.models.serviceResponse.providers.ResponseProvider;
import org.mockito.Mock;

abstract public class BaseServiceTest {
    @Mock
    protected UserRepository userRepository;
    @Mock
    protected ParticipantsRepository participantsRepository;
    @Mock
    protected SessionManager sessionManager;
    protected ResponseProvider responseProvider = ResponseProviderGenerator.generate();
}
