package com.eventPlanner.endpoints.account.accountService;

import com.eventPlanner.endpoints.BaseEndpointTest;
import com.eventPlanner.endpoints.account.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public abstract class BaseAccountServiceTest extends BaseEndpointTest {
    @InjectMocks
    protected AccountService accountService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        accountService = new AccountService(sessionManager, responseProvider, userDataService, participantDataService);
    }
}
