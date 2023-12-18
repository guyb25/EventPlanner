package com.eventPlanner.core.models.constraints.account;

import com.eventPlanner.core.config.validation.ValidationConstraints;
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
    String message() default "Password must be between " + ValidationConstraints.MIN_PASS_LEN +
            " and " + ValidationConstraints.MAX_PASS_LEN + " characters";;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
