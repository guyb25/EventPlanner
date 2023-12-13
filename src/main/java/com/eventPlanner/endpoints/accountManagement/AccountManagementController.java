package com.eventPlanner.endpoints.accountManagement;

import com.eventPlanner.models.dtos.UserRegistrationDto;
import com.eventPlanner.models.serviceResult.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountManagementController {
    private final AccountManagementService accountManagementService;

    @Autowired
    public AccountManagementController(AccountManagementService accountManagementService) {
        this.accountManagementService = accountManagementService;
    }

    @PostMapping("/accounts/create")
    public ResponseEntity<String> createAccount(@RequestBody UserRegistrationDto userDto) {
        ServiceResult res = accountManagementService.CreateUser(userDto.getName(), userDto.getPassword(), userDto.getEmail());
        return ResponseEntity.status(res.getHttpStatus()).body(res.getMessage());
    }
}
