package com.dilly.global.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomSizeValidator implements ConstraintValidator<CustomSize, CharSequence> {

    private int min;
    private int max;

    @Override
    public void initialize(CustomSize constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        int length = value.toString().replace("\r\n", "\n").length();

        return length >= min && length <= max;
    }
}
