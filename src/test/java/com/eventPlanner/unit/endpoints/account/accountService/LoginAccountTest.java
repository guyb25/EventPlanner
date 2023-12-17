package com.eventPlanner.unit.endpoints.account.accountService;

import com.eventPlanner.unit.testUtils.UniqueValueGenerator;
import com.eventPlanner.models.dtos.account.LoginAccountDto;
import com.eventPlanner.models.schemas.User;
import com.eventPlanner.models.responses.ServiceResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LoginAccountTest extends BaseAccountServiceTest {
    @Test
    public void testLoginAccount_WrongUsernameOrPassword_SessionNotCreatedAndReturnFailure() {
        // Arrange
        String wrongUsername = "wrongUsername";
        String wrongPassword = "wrongPassword";

        when(userDataService.doUsernameAndPasswordMatch(wrongUsername, wrongPassword)).thenReturn(false);
        ServiceResponse expectedResponse = responseProvider.account().wrongUsernameOrPassword();

        // Act
        ServiceResponse actualResponse = accountService.loginAccount(new LoginAccountDto(
                wrongUsername,
                wrongPassword
        ));

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(sessionManager, never()).createSession(any());
    }

    @Test
    public void testLoginAccount_ValidCredentials_SessionCreatedAndReturnSuccess() {
        // Arrange
        String sessionIdStub = UniqueValueGenerator.uniqueString();
        User userStub = new User("validUsername", "validPassword", "validEmail", 123L);

        when(userDataService.doUsernameAndPasswordMatch(userStub.getName(), userStub.getPassword())).thenReturn(true);
        when(userDataService.tryGetUserIdByName(userStub.getName())).thenReturn(userStub.getId());
        when(sessionManager.createSession(userStub.getId())).thenReturn(sessionIdStub);

        ServiceResponse expectedResponse = responseProvider.session().sessionCreated(sessionIdStub);

        // Act
        ServiceResponse actualResponse = accountService.loginAccount(new LoginAccountDto(
                userStub.getName(),
                userStub.getPassword()
        ));

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}
