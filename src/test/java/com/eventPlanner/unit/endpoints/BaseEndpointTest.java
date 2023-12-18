package com.eventPlanner.unit.endpoints;

import com.eventPlanner.dataAccess.sessions.SessionManager;
import com.eventPlanner.dataAccess.userEvents.services.ParticipantDataService;
import com.eventPlanner.dataAccess.userEvents.services.UserDataService;
import com.eventPlanner.core.models.responses.providers.ResponseProvider;
import org.mockito.Mock;

import static org.mockito.Mockito.when;

abstract public class BaseEndpointTest {
    @Mock
    protected UserDataService userDataService;
    @Mock
    protected ParticipantDataService participantDataService;
    @Mock
    protected SessionManager sessionManager;
    protected ResponseProvider responseProvider = ResponseProviderGenerator.generate();

    protected void mockInvalidSession(String sessionId) {
        when(sessionManager.missing(sessionId)).thenReturn(true);
    }

    protected void mockValidSession(String sessionId) {
        when(sessionManager.missing(sessionId)).thenReturn(false);
    }
}
