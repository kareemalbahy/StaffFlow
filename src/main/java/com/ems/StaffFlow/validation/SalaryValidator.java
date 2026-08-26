package com.ems.StaffFlow.validation;

import java.math.BigDecimal;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SalaryValidator implements ConstraintValidator<ValidSalary, BigDecimal> {
    private static final BigDecimal MINIMUM_VAL =new BigDecimal(1000.0);

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context){
        if(value==null)         return false;
        return value.compareTo(MINIMUM_VAL)>=0;
    }
}
