package com.eventPlanner.models.validation.constraints.account;

import com.eventPlanner.config.validation.ValidationConstraints;
import com.eventPlanner.models.validation.messages.AccountValidationMessages;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Size(
        min = ValidationConstraints.MIN_PASS_LEN,
        max = ValidationConstraints.MAX_PASS_LEN
)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default AccountValidationMessages.passwordSizeInvalid;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
