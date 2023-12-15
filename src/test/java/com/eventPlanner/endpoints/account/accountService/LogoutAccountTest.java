package com.eventPlanner.endpoints.account.accountService;

import com.eventPlanner.dataAccess.sessions.SessionManager;
import com.eventPlanner.dataAccess.userEvents.ParticipantsRepository;
import com.eventPlanner.dataAccess.userEvents.UserRepository;
import com.eventPlanner.endpoints.ResponseProviderGenerator;
import com.eventPlanner.endpoints.account.AccountService;
import com.eventPlanner.models.schemas.User;
import com.eventPlanner.models.serviceResponse.ServiceResponse;
import com.eventPlanner.models.serviceResponse.providers.ResponseProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class LogoutAccountTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ParticipantsRepository participantsRepository;
    @Mock
    private SessionManager sessionManager;
    private ResponseProvider responseProvider;
    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        responseProvider = ResponseProviderGenerator.generate();
        accountService = new AccountService(userRepository, participantsRepository, sessionManager, responseProvider);
    }

    @Test
    public void testLogoutAccount_InvalidSession_SessionNotDeletedAndReturnFailure() {
        // Arrange
        String sessionIdStub = "sessionIdStub";
        when(sessionManager.missing(sessionIdStub)).thenReturn(true);
        var expectedResponse = responseProvider.session().invalidSession();

        // Act
        var actualResponse = accountService.logoutAccount(sessionIdStub);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(sessionManager, never()).endSession(any());
    }

    @Test
    public void testLoginAccount_ValidSession_SessionDeletedAndReturnSuccess() {
        // Arrange
        String sessionIdStub = "sessionIdStub";
        when(sessionManager.missing(sessionIdStub)).thenReturn(false);
        var expectedResponse = responseProvider.session().sessionEnded();

        // Act
        var actualResponse = accountService.logoutAccount(sessionIdStub);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(sessionManager, times(1)).endSession(sessionIdStub);
    }
}
