package com.ems.employee_management_system.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ems.employee_management_system.dto.EmployeeRequestDto;
import com.ems.employee_management_system.dto.EmployeeResponseDto;
import com.ems.employee_management_system.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(
    name="Employee API",
    description = "Provides operations to create, retrieve, update, delete, search, and paginate employees"
)
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(
            summary = "Create Employee",
            description = "Creates a new employee in the system. Ensures email is unique."
    )
    @ApiResponse(responseCode="201", description="Employee created successfully")
    @ApiResponse(responseCode="400", description="Invalid request payload or duplicate email")
    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(
            @Valid @RequestBody EmployeeRequestDto requestDto) {
        EmployeeResponseDto responseDto = employeeService.createEmployee(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Employee by ID",
            description = "Retrieves details of an active employee matching the specified ID."
    )
    @ApiResponse(responseCode = "200", description = "Employee details retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Employee not found or soft-deleted")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(
            @PathVariable Long id) {
        EmployeeResponseDto responseDto = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(
            summary = "Get All Employees",
            description = "Fetches a paginated, sorted, and optionally filtered list of all active employees."
    )
    @ApiResponse(responseCode = "200", description = "Paginated list retrieved successfully")
    @GetMapping
    public ResponseEntity<Page<EmployeeResponseDto>> getAllEmployees(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "department", required = false) String department
    ) {
        Page<EmployeeResponseDto> employees = employeeService.getAllEmployees(
                pageNo, pageSize, sortBy, sortDir, firstName, department);
        return ResponseEntity.ok(employees);
    }

    @Operation(
            summary = "Update Employee",
            description = "Updates an existing employee details. Checks for duplicate email."
    )
    @ApiResponse(responseCode = "200", description = "Employee updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request payload or duplicate email")
    @ApiResponse(responseCode = "404", description = "Employee not found")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDto requestDto){
        EmployeeResponseDto responseDto = employeeService.updateEmployee(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(
            summary = "Delete Employee (Soft Delete)",
            description = "Performs soft deletion by marking the employee active status to false."
    )
    @ApiResponse(responseCode = "204", description = "Employee soft-deleted successfully")
    @ApiResponse(responseCode = "404", description = "Employee not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
