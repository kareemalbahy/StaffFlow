package com.ems.StaffFlow.exception;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;
    private Map<String , String> validationErrors;

    public ErrorDetails(LocalDateTime timestamp, String message , String details){
        this.timestamp=timestamp;
        this.message=message;
        this.details=details;
    }
}
