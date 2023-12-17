package com.eventPlanner.core.models.responses.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResponseProvider {
    private final AccountResponseProvider accountResponseProvider;
    private final EventResponseProvider eventResponseProvider;
    private final SessionResponseProvider sessionResponseProvider;
    private final GeneralResponseProvider generalResponseProvider;

    @Autowired
    public ResponseProvider(AccountResponseProvider accountResponseProvider, EventResponseProvider eventResponseProvider,
                            SessionResponseProvider sessionResponseProvider, GeneralResponseProvider generalResponseProvider) {

        this.accountResponseProvider = accountResponseProvider;
        this.eventResponseProvider = eventResponseProvider;
        this.sessionResponseProvider = sessionResponseProvider;
        this.generalResponseProvider = generalResponseProvider;
    }

    public AccountResponseProvider account() {
        return accountResponseProvider;
    }

    public EventResponseProvider event() {
        return eventResponseProvider;
    }

    public SessionResponseProvider session() {
        return sessionResponseProvider;
    }

    public GeneralResponseProvider general() {
        return generalResponseProvider;
    }
}
