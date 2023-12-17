package com.eventPlanner.models.validation.messages;

import com.eventPlanner.config.validation.ValidationConstraints;

public class AccountValidationMessages {
    public static final String usernameSizeInvalid = "Username must be between " + ValidationConstraints.MIN_USER_LEN +
            " and " + ValidationConstraints.MAX_USER_LEN + " characters";
    public static final String passwordSizeInvalid = "Password must be between " + ValidationConstraints.MIN_PASS_LEN +
            " and " + ValidationConstraints.MAX_PASS_LEN + " characters";
}
