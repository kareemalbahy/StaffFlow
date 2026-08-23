package com.ems.employee_management_system.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy=SalaryValidator.class)
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSalary {
    String message() default  "Salary must be equal to or greater than the minimum wage threshold of 1,000.00";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
