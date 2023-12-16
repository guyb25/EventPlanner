package com.eventPlanner.endpoints;

import com.eventPlanner.models.responses.providers.*;

public class ResponseProviderGenerator {
    public static ResponseProvider generate() {
        return new ResponseProvider(
                new AccountResponseProvider(),
                new EventResponseProvider(),
                new SessionResponseProvider(),
                new GeneralResponseProvider());
    }
}