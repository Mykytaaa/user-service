package com.iprody.user.profile.userprofileservice.rest.validation.validator;

import com.iprody.user.profile.userprofileservice.rest.validation.annotation.ValidateStrings;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ValidateStringValidator implements ConstraintValidator<ValidateStrings, String> {

    /**
     * An array of valid strings specified in the associated {@code ValidateString}
     * annotation.
     */
    private String[] validStrings;

    /**
     * Initializes the validator with the values from the {@code ValidateString} annotation.
     *
     * @param constraintAnnotation the annotation instance containing the validation details.
     */
    @Override
    public void initialize(ValidateStrings constraintAnnotation) {
        this.validStrings = constraintAnnotation.value();
    }

    /**
     * Validates whether the provided string value matches any of the valid values.
     *
     * @param validValue the string value to be validated.
     * @param context    the context in which the constraint is evaluated.
     * @return {@code true} if the value is valid, {@code false} otherwise.
     */
    @Override
    public boolean isValid(String validValue, ConstraintValidatorContext context) {
        return Arrays.asList(validStrings).contains(validValue);
    }
}
