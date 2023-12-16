package com.eventPlanner.endpoints.account.accountService;

import com.eventPlanner.testUtils.UniqueValueGenerator;
import com.eventPlanner.models.dtos.account.LogoutAccountDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LogoutAccountTest extends BaseAccountServiceTest {
    @Test
    public void testLogoutAccount_InvalidSession_SessionNotDeletedAndReturnFailure() {
        // Arrange
        var logoutAccountDto = new LogoutAccountDto(UniqueValueGenerator.uniqueString());
        var expectedResponse = responseProvider.session().invalidSession();
        mockInvalidSession(logoutAccountDto.sessionId());

        // Act
        var actualResponse = accountService.logoutAccount(logoutAccountDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(sessionManager, never()).endSession(any());
    }

    @Test
    public void testLoginAccount_ValidSession_SessionDeletedAndReturnSuccess() {
        // Arrange
        var logoutAccountDto = new LogoutAccountDto(UniqueValueGenerator.uniqueString());
        var sessionIdStub = logoutAccountDto.sessionId();
        var expectedResponse = responseProvider.session().sessionEnded();
        mockValidSession(logoutAccountDto.sessionId());

        // Act
        var actualResponse = accountService.logoutAccount(logoutAccountDto);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(sessionManager, times(1)).endSession(sessionIdStub);
    }
}
