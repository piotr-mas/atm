package com.piotr.atm.validator;

import com.piotr.atm.validator.constraints.ValidateCash;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * Custom validation class for @ValidCash constraint
 * Check if customer won't request invalid amount of cash.
 * The number must finish either with 0 or 5.
 */
public class CheckCashFormatValidator implements ConstraintValidator<ValidateCash, Integer> {


    @Override
    public void initialize(ValidateCash constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer cash, ConstraintValidatorContext constraintValidatorContext) {
        String sCash = cash.toString();
        return (sCash.endsWith("0") || sCash.endsWith("5"));
    }
}
