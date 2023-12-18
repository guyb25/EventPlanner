package com.eventPlanner.integration.account;

import com.eventPlanner.integration.BaseIntegrationTest;
import com.eventPlanner.core.models.dtos.account.CreateAccountDto;
import com.eventPlanner.dataAccess.userEvents.schemas.User;
import com.eventPlanner.testUtils.UniqueValueGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterTests extends BaseIntegrationTest {
    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    @Rollback
    public void testCreateAccountRoute_Valid_UserCreatedAndReturnConflict() {
        // Arrange
        var requestDto = new CreateAccountDto("TestUser", "TestPassword", "test@example.com");

        // Act
        var response = restTemplate.postForEntity("/accounts/create", requestDto, String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(userRepository.existsUserByName(requestDto.name())).isTrue();
    }

    @Test
    @Rollback()
    public void testCreateAccountRoute_UsernameTaken_UserNotCreatedAndReturnConflict() {
        // Arrange
        var user = new User("TestUser", "TestPassword", "test@example.com");
        userRepository.saveAndFlush(user);

        var requestDto = new CreateAccountDto(
                user.getName(),
                UniqueValueGenerator.uniqueString(),
                "test2@example.com"
        );

        // Act
        var response = restTemplate.postForEntity("/accounts/create", requestDto, String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @Rollback()
    public void testCreateAccountRoute_EmailTaken_UserNotCreatedAndReturnConflict() {
        // Arrange
        var user = new User("TestUser", "TestPassword", "test@example.com");
        userRepository.saveAndFlush(user);

        var requestDto = new CreateAccountDto(
                UniqueValueGenerator.uniqueString(),
                UniqueValueGenerator.uniqueString(),
                user.getEmail()
        );

        // Act
        var response = restTemplate.postForEntity("/accounts/create", requestDto, String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }
}
