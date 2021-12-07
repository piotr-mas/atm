package com.piotr.atm.validator.constraints;

import com.piotr.atm.validator.CheckCashFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CheckCashFormatValidator.class)
public @interface ValidateCash {
    //required
    String format() default "";

    String message() default "Incorrect requested cash - requested cash must and with 0 or 5";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
