package com.eventPlanner.models.validation.constraints.event;

import com.eventPlanner.config.validation.ValidationConstraints;
import com.eventPlanner.models.validation.messages.AccountValidationMessages;
import com.eventPlanner.models.validation.messages.EventValidationMessages;
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
    String message() default EventValidationMessages.eventNameSizeInvalid;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
