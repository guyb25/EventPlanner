package com.eventPlanner.core.models.constraints.account;


import com.eventPlanner.core.config.validation.ValidationConstraints;
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
    String message() default "Username must be between " + ValidationConstraints.MIN_USER_LEN +
            " and " + ValidationConstraints.MAX_USER_LEN + " characters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
