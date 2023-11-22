package com.iprody.user.profile.userprofileservice.rest.validation.annotation;

import com.iprody.user.profile.userprofileservice.rest.validation.validator.ValidateStringValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to validate that a string parameter matches one of the specified valid values.
 * The validation is performed by the {@link ValidateStringValidator}.
 *
 * Usage example:
 * ```java
 * &#64;ValidateStrings({"value1", "value2", "value3"})
 * public void myMethod(String myParameter) {
 *     // Method body
 * }
 * ```
 */
@Documented
@Constraint(validatedBy = ValidateStringValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ValidateStrings {

    /**
     * An array of strings representing the valid values for the annotated parameter.
     *
     * @return the valid values for the annotated parameter.
     */
    String[] value();

    /**
     * The error message to be used when validation fails.
     *
     * @return the error message for validation failure.
     */
    String message() default "Invalid values. Should be String only";

    /**
     * The validation groups to which this constraint belongs.
     *
     * @return the validation groups for this constraint.
     */
    Class<?>[] groups() default {};

    /**
     * Payload to associate with the constraint.
     *
     * @return the payload for this constraint.
     */
    Class<? extends Payload>[] payload() default {};
}
