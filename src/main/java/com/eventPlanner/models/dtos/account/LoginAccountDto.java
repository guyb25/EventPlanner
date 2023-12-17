package com.eventPlanner.models.dtos.account;

import com.eventPlanner.models.validation.constraints.account.PasswordConstraint;
import com.eventPlanner.models.validation.constraints.account.UserConstraint;

public record LoginAccountDto(
        @UserConstraint String name,
        @PasswordConstraint
        String password) {
}
