package com.eventPlanner.endpoints.account;

import com.eventPlanner.models.dtos.account.LoginAccountDto;
import com.eventPlanner.models.dtos.account.LogoutAccountDto;
import com.eventPlanner.models.dtos.account.CreateAccountDto;
import com.eventPlanner.models.dtos.account.DeleteAccountDto;
import com.eventPlanner.models.serviceResponse.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceProxy {
    private final AccountService accountManagementService;

    @Autowired
    public AccountServiceProxy(AccountService accountManagementService) {
        this.accountManagementService = accountManagementService;
    }

    public ServiceResponse<String> createAccount(CreateAccountDto createAccountDto) {
        return accountManagementService.createAccount(createAccountDto.name(), createAccountDto.password(), createAccountDto.email());
    }

    public ServiceResponse<String> loginAccount(LoginAccountDto loginAccountDto) {
        return accountManagementService.loginAccount(loginAccountDto.name(), loginAccountDto.password());
    }

    public ServiceResponse<String> logoutAccount(LogoutAccountDto logoutAccountDto) {
        return accountManagementService.logoutAccount(logoutAccountDto.sessionId());
    }

    public ServiceResponse<String> deleteAccount(DeleteAccountDto deleteAccountDto) {
        return accountManagementService.deleteAccount(deleteAccountDto.sessionId());
    }
}
