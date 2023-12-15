package com.eventPlanner.endpoints.account.accountService;

import static org.assertj.core.api.Assertions.assertThat;

import com.eventPlanner.models.dtos.account.DeleteAccountDto;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DeleteAccountTest extends BaseAccountServiceTest {
    private final DeleteAccountDto deleteAccountDto = new DeleteAccountDto("sessionIdStub");
    @Test
    public void testDeleteAccount_InvalidSession_AccountNotDeletedAndReturnFailure() {
        // Arrange
        var expectedResponse = responseProvider.session().invalidSession();

        when(sessionManager.missing(deleteAccountDto.sessionId())).thenReturn(true);

        // Act
        var actualResponse = accountService.deleteAccount(deleteAccountDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(participantDataService, never()).deleteAllByUserId(any());
        verify(userDataService, never()).deleteUserById(any());
        verify(sessionManager, never()).endSession(any());
    }

    @Test
    public void testDeleteAccount_ValidSession_AccountDeletedAndSessionEndedAndReturnSuccess() {
        // Arrange
        var sessionIdStub = deleteAccountDto.sessionId();
        var userIdStub = 123L;
        var expectedResponse = responseProvider.account().userDeleted();

        when(sessionManager.missing(sessionIdStub)).thenReturn(false);
        when(sessionManager.getUserIdFromSession(sessionIdStub)).thenReturn(userIdStub);

        // Act
        var actualResponse = accountService.deleteAccount(deleteAccountDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(participantDataService, times(1)).deleteAllByUserId(userIdStub);
        verify(userDataService, times(1)).deleteUserById(userIdStub);
        verify(sessionManager, times(1)).endSession(sessionIdStub);
    }
}
