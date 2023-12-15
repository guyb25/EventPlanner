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

public class LoginAccountTest {
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
    public void testLoginAccount_WrongUsernameOrPassword_SessionNotCreatedAndReturnFailure() {
        // Arrange
        String wrongUsername = "wrongUsername";
        String wrongPassword = "wrongPassword";

        when(userRepository.existsByNameAndPassword(wrongUsername, wrongPassword)).thenReturn(false);
        ServiceResponse expectedResponse = responseProvider.account().wrongUsernameOrPassword();

        // Act
        ServiceResponse actualResponse = accountService.loginAccount(wrongUsername, wrongPassword);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(userRepository, never()).findUserByName(wrongUsername);
        verify(sessionManager, never()).createSession(any());
    }

    @Test
    public void testLoginAccount_ValidCredentials_SessionCreatedAndReturnSuccess() {
        // Arrange
        String sessionIdStub = "sessionIdStub";
        User userStub = new User("validUsername", "validPassword", "validEmail", 123L);

        when(userRepository.existsByNameAndPassword(userStub.getName(), userStub.getPassword())).thenReturn(true);
        when(userRepository.findUserByName(userStub.getName())).thenReturn(userStub);
        when(sessionManager.createSession(userStub.getId())).thenReturn(sessionIdStub);

        ServiceResponse expectedResponse = responseProvider.session().sessionCreated(sessionIdStub);

        // Act
        ServiceResponse actualResponse = accountService.loginAccount(userStub.getName(), userStub.getPassword());

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}
