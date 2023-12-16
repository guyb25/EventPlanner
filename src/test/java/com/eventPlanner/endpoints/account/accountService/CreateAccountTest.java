package com.eventPlanner.endpoints.account.accountService;

import com.eventPlanner.models.dtos.account.CreateAccountDto;
import com.eventPlanner.models.schemas.User;
import com.eventPlanner.models.serviceResponse.ServiceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateAccountTest extends BaseAccountServiceTest {
    private final String takenUsernameStub = "takenUsername";
    private final String availableUsernameStub = "availableUsername";
    private final String takenEmailStub = "takenEmail";
    private final String availableEmailStub = "availableEmailStub";
    private final String validPassword = "validPassword";

    @BeforeEach
    public void setup() {
        super.setup();
        when(userDataService.isUsernameTaken(takenUsernameStub)).thenReturn(true);
        when(userDataService.isUsernameTaken(availableUsernameStub)).thenReturn(false);
        when(userDataService.isEmailTaken(takenEmailStub)).thenReturn(true);
        when(userDataService.isEmailTaken(availableEmailStub)).thenReturn(false);
    }

    @Test
    public void testCreateAccount_EmailTaken_UserNotSavedAndReturnsEmailTaken() {
        // Arrange
        ServiceResponse expectedResponse = responseProvider.account().emailTaken();

        // Act
        ServiceResponse actualResponse = accountService.createAccount(new CreateAccountDto(
                availableUsernameStub,
                validPassword,
                takenEmailStub
        ));

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(userDataService, never()).saveUser(any(User.class));
    }

    @Test
    public void testCreateAccount_UserTaken_UserNotSavedAndReturnsUserTaken() {
        // Arrange
        ServiceResponse expectedResponse = responseProvider.account().usernameTaken();

        // Act
        ServiceResponse actualResponse = accountService.createAccount(new CreateAccountDto(
                takenUsernameStub,
                validPassword,
                availableEmailStub
        ));

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(userDataService, never()).saveUser(any());
    }

    @Test
    public void testCreateAccount_UserAndEmailAvailable_UserSavedAndReturnedUserCreated() {
        // Arrange
        ServiceResponse expectedResponse = responseProvider.account().userCreated();
        User expectedUserToBeSaved = new User(availableUsernameStub, validPassword, availableEmailStub);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        // Act
        ServiceResponse actualResponse = accountService.createAccount(new CreateAccountDto(
                availableUsernameStub,
                validPassword,
                availableEmailStub
        ));

        // Assert
        verify(userDataService, times(1)).saveUser(userCaptor.capture());
        assertThat(userCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedUserToBeSaved);
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}
