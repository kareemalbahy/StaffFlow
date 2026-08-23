package com.ems.employee_management_system.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ems.employee_management_system.validation.ValidSalary;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeRequestDto {

    @NotBlank(message="first name is required")
    @Size(max=50 , message="first name cannot exceed 50 characters")
    private String firstName;

    @NotBlank(message="last name is required")
    @Size(max=50 , message="last name cannot exceed 50 characters")
    private String lastName;

    @NotBlank(message="email is required")
    @Email(message="pleease provide a valid email address")
    @Size(max=100 , message="email cannot exceed 50 characters")
    private String email;

    @NotBlank(message="email is required")
    @Pattern(regexp="^\\+[1-9]\\d{7,14}$", message="please provide a valid phone number")
    private String phone;

    @NotBlank(message="department is required")
    @Size(max=100 , message="department cannot exceed 100 characters")
    private String department;

    @NotBlank(message="position is required")
    @Size(max=50 , message="position cannot exceed 50 characters")
    private String position;

    @NotNull(message="hire date is required")
    @ValidSalary
    private BigDecimal salary;

    @NotNull(message="hire date is required")
    @PastOrPresent(message="hire date must be in the past or present")
    private LocalDate hireDate;
}
