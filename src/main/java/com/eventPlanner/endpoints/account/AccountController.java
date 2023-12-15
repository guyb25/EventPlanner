package com.eventPlanner.endpoints.account;

import com.eventPlanner.models.dtos.account.LoginAccountDto;
import com.eventPlanner.models.dtos.account.LogoutAccountDto;
import com.eventPlanner.models.dtos.account.CreateAccountDto;
import com.eventPlanner.models.dtos.account.DeleteAccountDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Account Management")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody CreateAccountDto createAccountDto) {
        return accountService.createAccount(createAccountDto).toResponse();
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginAccount(@RequestBody LoginAccountDto loginAccountDto) {
        return accountService.loginAccount(loginAccountDto).toResponse();
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logoutAccount(@RequestBody LogoutAccountDto logoutAccountDto) {
        return accountService.logoutAccount(logoutAccountDto).toResponse();
    }

    @DeleteMapping("/terminate")
    public ResponseEntity<String> deleteAccount(@RequestBody DeleteAccountDto deleteAccountDto) {
        return accountService.deleteAccount(deleteAccountDto).toResponse();
    }
}
