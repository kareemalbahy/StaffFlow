package com.ems.employee_management_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, String fieldName , Object fieldValue){
        super(String.format("%s not found with %s : %s", resource , fieldName , fieldValue));
    }
}
