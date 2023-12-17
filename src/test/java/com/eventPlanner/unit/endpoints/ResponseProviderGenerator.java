package com.eventPlanner.unit.endpoints;

import com.eventPlanner.core.models.responses.providers.*;

public class ResponseProviderGenerator {
    public static ResponseProvider generate() {
        return new ResponseProvider(
                new AccountResponseProvider(),
                new EventResponseProvider(),
                new SessionResponseProvider(),
                new GeneralResponseProvider());
    }
}