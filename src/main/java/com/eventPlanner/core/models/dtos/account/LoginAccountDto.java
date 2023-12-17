package com.eventPlanner.core.models.dtos.account;

import com.eventPlanner.core.models.constraints.account.UserConstraint;
import com.eventPlanner.core.models.constraints.account.PasswordConstraint;

public record LoginAccountDto(
        @UserConstraint String name,
        @PasswordConstraint
        String password) {
}
