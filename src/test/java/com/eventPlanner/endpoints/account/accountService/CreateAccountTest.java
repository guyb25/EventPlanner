package com.eventPlanner.endpoints.account.accountService;

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
        when(userRepository.existsUserByName(takenUsernameStub)).thenReturn(true);
        when(userRepository.existsUserByName(availableUsernameStub)).thenReturn(false);
        when(userRepository.existsUserByEmail(takenEmailStub)).thenReturn(true);
        when(userRepository.existsUserByEmail(availableEmailStub)).thenReturn(false);
    }

    @Test
    public void testCreateAccount_EmailTaken_UserNotSavedAndReturnsEmailTaken() {
        // Arrange
        ServiceResponse expectedResponse = responseProvider.account().emailTaken();

        // Act
        ServiceResponse actualResponse = accountService.createAccount(availableUsernameStub, validPassword, takenEmailStub);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testCreateAccount_UserTaken_UserNotSavedAndReturnsUserTaken() {
        // Arrange
        ServiceResponse expectedResponse = responseProvider.account().usernameTaken();

        // Act
        ServiceResponse actualResponse = accountService.createAccount(takenUsernameStub, validPassword, availableEmailStub);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testCreateAccount_UserAndEmailAvailable_UserSavedAndReturnedUserCreated() {
        // Arrange
        ServiceResponse expectedResponse = responseProvider.account().userCreated();
        User expectedUserToBeSaved = new User(availableUsernameStub, validPassword, availableEmailStub);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        // Act
        ServiceResponse actualResponse = accountService.createAccount(availableUsernameStub, validPassword, availableEmailStub);

        // Assert
        verify(userRepository, times(1)).save(userCaptor.capture());
        assertThat(userCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedUserToBeSaved);
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}
