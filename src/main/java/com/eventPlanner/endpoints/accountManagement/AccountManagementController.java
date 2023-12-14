package com.eventPlanner.endpoints.accountManagement;

import com.eventPlanner.models.dtos.accountManagement.UserLoginDto;
import com.eventPlanner.models.dtos.accountManagement.UserLogoutDto;
import com.eventPlanner.models.dtos.accountManagement.UserRegistrationDto;
import com.eventPlanner.models.dtos.accountManagement.UserTerminationDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Account Management")
public class AccountManagementController {
    private final AccountManagementService accountManagementService;

    @Autowired
    public AccountManagementController(AccountManagementService accountManagementService) {
        this.accountManagementService = accountManagementService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody UserRegistrationDto regDto) {
        return accountManagementService
                .CreateUser(regDto.name(), regDto.password(), regDto.email())
                .toResponse();
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginAccount(@RequestBody UserLoginDto loginDto) {
        return accountManagementService
                .LoginUser(loginDto.name(), loginDto.password())
                .toResponse();
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logoutAccount(@RequestBody UserLogoutDto logoutDto) {
        return accountManagementService
                .LogoutUser(logoutDto.sessionId())
                .toResponse();
    }

    @DeleteMapping("/terminate")
    public ResponseEntity<String> deleteAccount(@RequestBody UserTerminationDto userTerminationDto) {
        return accountManagementService
                .DeleteUser(userTerminationDto.sessionId())
                .toResponse();
    }
}
