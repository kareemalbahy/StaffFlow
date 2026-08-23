package com.ems.employee_management_system.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;

import com.ems.employee_management_system.dto.EmployeeRequestDto;
import com.ems.employee_management_system.dto.EmployeeResponseDto;

public interface EmployeeService {
    
    EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto);

    EmployeeResponseDto getEmployeeById(Long id);

    Page<EmployeeResponseDto> getAllEmployees(
        int pageNo, int pageSize, String sortBy, String sortDir, String firstName, String Department);

    EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto employeeRequestDto);

    void deleteEmployee(Long id);

    Long countTotalActive();

    BigDecimal getAverageSalary();
}
