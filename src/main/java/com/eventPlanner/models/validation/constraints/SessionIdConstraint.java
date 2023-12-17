package com.eventPlanner.models.validation.constraints;

import com.eventPlanner.models.validation.messages.ValidationMessages;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@NotEmpty()
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionIdConstraint {
    String message() default ValidationMessages.required;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
