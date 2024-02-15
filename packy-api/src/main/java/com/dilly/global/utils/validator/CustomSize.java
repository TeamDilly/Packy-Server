package com.dilly.global.utils.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({
    java.lang.annotation.ElementType.FIELD,
    java.lang.annotation.ElementType.METHOD,
    java.lang.annotation.ElementType.PARAMETER,
    java.lang.annotation.ElementType.ANNOTATION_TYPE,
    java.lang.annotation.ElementType.CONSTRUCTOR,
    java.lang.annotation.ElementType.TYPE_USE})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CustomSizeValidator.class})
public @interface CustomSize {

    String message() default "{javax.validation.constraints.Size.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int min() default 0;
    int max() default Integer.MAX_VALUE;
}
