package com.eventPlanner.endpoints.account.accountService;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DeleteAccountTest extends BaseAccountServiceTest {
    @Test
    public void testDeleteAccount_InvalidSession_AccountNotDeletedAndReturnFailure() {
        // Arrange
        var sessionIdStub = "sessionIdStub";
        var expectedResponse = responseProvider.session().invalidSession();

        when(sessionManager.missing(sessionIdStub)).thenReturn(true);

        // Act
        var actualResponse = accountService.deleteAccount(sessionIdStub);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(participantsRepository, never()).deleteAllByUserId(any());
        verify(userRepository, never()).deleteById(any());
        verify(sessionManager, never()).endSession(any());
    }

    @Test
    public void testDeleteAccount_ValidSession_AccountDeletedAndSessionEndedAndReturnSuccess() {
        // Arrange
        var sessionIdStub = "sessionIdStub";
        var userIdStub = 123L;
        var expectedResponse = responseProvider.account().userDeleted();

        when(sessionManager.missing(sessionIdStub)).thenReturn(false);
        when(sessionManager.getUserIdFromSession(sessionIdStub)).thenReturn(userIdStub);

        // Act
        var actualResponse = accountService.deleteAccount(sessionIdStub);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(participantsRepository, times(1)).deleteAllByUserId(userIdStub);
        verify(userRepository, times(1)).deleteById(userIdStub);
        verify(sessionManager, times(1)).endSession(sessionIdStub);
    }
}
