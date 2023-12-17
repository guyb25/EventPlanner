package com.eventPlanner.core.models.constraints.event;

import com.eventPlanner.core.config.validation.ValidationConstraints;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Size(
        min = ValidationConstraints.MIN_EVENT_NAME_LEN,
        max = ValidationConstraints.MAX_EVENT_NAME_LEN
)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventNameConstraint {
    String message() default "Event name must be between " +
            ValidationConstraints.MIN_EVENT_NAME_LEN + " and " + ValidationConstraints.MAX_EVENT_NAME_LEN +
            " characters";;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
