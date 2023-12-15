package com.eventPlanner.endpoints.account.accountService;

import static org.assertj.core.api.Assertions.assertThat;

import com.eventPlanner.dummyBuilders.UniqueValueGenerator;
import com.eventPlanner.models.dtos.account.DeleteAccountDto;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DeleteAccountTest extends BaseAccountServiceTest {
    @Test
    public void testDeleteAccount_InvalidSession_AccountNotDeletedAndReturnFailure() {
        // Arrange
        var deleteAccountDto = new DeleteAccountDto(UniqueValueGenerator.uniqueString());
        var expectedResponse = responseProvider.session().invalidSession();

        mockInvalidSession(deleteAccountDto.sessionId());

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
        var dto = new DeleteAccountDto(UniqueValueGenerator.uniqueString());
        var userIdStub = UniqueValueGenerator.uniqueLong();
        var expectedResponse = responseProvider.account().userDeleted();

        mockValidSession(dto.sessionId());
        when(sessionManager.getUserIdFromSession(dto.sessionId())).thenReturn(userIdStub);

        // Act
        var actualResponse = accountService.deleteAccount(dto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(participantDataService, times(1)).deleteAllByUserId(userIdStub);
        verify(userDataService, times(1)).deleteUserById(userIdStub);
        verify(sessionManager, times(1)).endSession(dto.sessionId());
    }
}
