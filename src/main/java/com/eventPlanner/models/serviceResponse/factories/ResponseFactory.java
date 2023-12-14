package com.eventPlanner.models.serviceResponse.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResponseFactory {
    private final AccountResponseFactory accountResponseFactory;
    private final EventResponseFactory eventResponseFactory;
    private final SessionResponseFactory sessionResponseFactory;
    private final GeneralResponseFactory generalResponseFactory;

    @Autowired
    ResponseFactory(AccountResponseFactory accountResponseFactory, EventResponseFactory eventResponseFactory,
                    SessionResponseFactory sessionResponseFactory, GeneralResponseFactory generalResponseFactory) {

        this.accountResponseFactory = accountResponseFactory;
        this.eventResponseFactory = eventResponseFactory;
        this.sessionResponseFactory = sessionResponseFactory;
        this.generalResponseFactory = generalResponseFactory;
    }

    public AccountResponseFactory account() {
        return accountResponseFactory;
    }

    public EventResponseFactory event() {
        return eventResponseFactory;
    }

    public SessionResponseFactory session() {
        return sessionResponseFactory;
    }

    public GeneralResponseFactory general() {
        return generalResponseFactory;
    }
}
