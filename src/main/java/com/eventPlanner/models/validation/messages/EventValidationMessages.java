package com.eventPlanner.models.validation.messages;

import com.eventPlanner.config.validation.ValidationConstraints;

public class EventValidationMessages {
    public static final String eventNameSizeInvalid = "Event name must be between " +
            ValidationConstraints.MIN_EVENT_NAME_LEN + " and " + ValidationConstraints.MAX_EVENT_NAME_LEN +
            " characters";
}
