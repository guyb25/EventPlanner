package com.eventPlanner.models.validation.constraints.account;


import com.eventPlanner.config.validation.ValidationConstraints;
import com.eventPlanner.models.validation.messages.AccountValidationMessages;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {})
@Size(
        min = ValidationConstraints.MIN_USER_LEN,
        max = ValidationConstraints.MAX_USER_LEN
)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserConstraint {
    String message() default AccountValidationMessages.usernameSizeInvalid;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
