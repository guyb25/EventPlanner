package com.eventPlanner.endpoints.account;

import com.eventPlanner.models.dtos.account.UserLoginDto;
import com.eventPlanner.models.dtos.account.UserLogoutDto;
import com.eventPlanner.models.dtos.account.UserRegistrationDto;
import com.eventPlanner.models.dtos.account.UserTerminationDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Account Management")
public class AccountController {
    private final AccountService accountManagementService;

    @Autowired
    public AccountController(AccountService accountManagementService) {
        this.accountManagementService = accountManagementService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody UserRegistrationDto regDto) {
        return accountManagementService
                .createUser(regDto.name(), regDto.password(), regDto.email())
                .toResponse();
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginAccount(@RequestBody UserLoginDto loginDto) {
        return accountManagementService
                .loginUser(loginDto.name(), loginDto.password())
                .toResponse();
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logoutAccount(@RequestBody UserLogoutDto logoutDto) {
        return accountManagementService
                .logoutUser(logoutDto.sessionId())
                .toResponse();
    }

    @DeleteMapping("/terminate")
    public ResponseEntity<String> deleteAccount(@RequestBody UserTerminationDto userTerminationDto) {
        return accountManagementService
                .deleteUser(userTerminationDto.sessionId())
                .toResponse();
    }
}
