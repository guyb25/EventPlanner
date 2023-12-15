package com.eventPlanner.endpoints.account.accountService;

import com.eventPlanner.models.dtos.account.LogoutAccountDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class LogoutAccountTest extends BaseAccountServiceTest {
    private final LogoutAccountDto logoutAccountDto = new LogoutAccountDto("sessionIdStub");

    @Test
    public void testLogoutAccount_InvalidSession_SessionNotDeletedAndReturnFailure() {
        // Arrange
        when(sessionManager.missing(logoutAccountDto.sessionId())).thenReturn(true);
        var expectedResponse = responseProvider.session().invalidSession();

        // Act
        var actualResponse = accountService.logoutAccount(logoutAccountDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(sessionManager, never()).endSession(any());
    }

    @Test
    public void testLoginAccount_ValidSession_SessionDeletedAndReturnSuccess() {
        // Arrange
        String sessionIdStub = logoutAccountDto.sessionId();
        when(sessionManager.missing(sessionIdStub)).thenReturn(false);
        var expectedResponse = responseProvider.session().sessionEnded();

        // Act
        var actualResponse = accountService.logoutAccount(logoutAccountDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(sessionManager, times(1)).endSession(sessionIdStub);
    }
}
