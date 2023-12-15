package com.eventPlanner.endpoints;

import com.eventPlanner.dataAccess.sessions.SessionManager;
import com.eventPlanner.dataAccess.userEvents.services.ParticipantDataService;
import com.eventPlanner.dataAccess.userEvents.services.UserDataService;
import com.eventPlanner.models.serviceResponse.providers.ResponseProvider;
import org.mockito.Mock;

abstract public class BaseServiceTest {
    @Mock
    protected UserDataService userDataService;
    @Mock
    protected ParticipantDataService participantDataService;
    @Mock
    protected SessionManager sessionManager;
    protected ResponseProvider responseProvider = ResponseProviderGenerator.generate();
}
