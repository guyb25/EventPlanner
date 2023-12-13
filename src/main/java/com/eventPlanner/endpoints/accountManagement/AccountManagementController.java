package com.eventPlanner.endpoints.accountManagement;

import com.eventPlanner.models.dtos.UserLoginDto;
import com.eventPlanner.models.dtos.UserRegistrationDto;
import com.eventPlanner.models.serviceResult.ServiceResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        ServiceResult<String> res = accountManagementService.CreateUser(regDto.getName(), regDto.getPassword(), regDto.getEmail());
        return ResponseEntity.status(res.getHttpStatus()).body(res.getMessage());
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginAccount(@RequestBody UserLoginDto loginDto) {
        ServiceResult<String> res = accountManagementService.LoginUser(loginDto.getName(), loginDto.getPassword());
        return ResponseEntity.status(res.getHttpStatus()).body(res.getMessage());
    }
}
