package com.ems.employee_management_system.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ems.employee_management_system.dto.EmployeeRequestDto;
import com.ems.employee_management_system.dto.EmployeeResponseDto;
import com.ems.employee_management_system.service.EmployeeService;

import jakarta.validation.Valid;

@Controller
public class EmployeeViewController {

    private final EmployeeService employeeService;

    public EmployeeViewController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String showDashboard(
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "department", required = false) String department,
            @RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "id", required = false) Long id,
            Model model
    ) {
        Page<EmployeeResponseDto> employeePage = employeeService.getAllEmployees(
                pageNo, pageSize, sortBy, sortDir, firstName, department);

        model.addAttribute("employees", employeePage.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("firstName", firstName != null ? firstName : "");
        model.addAttribute("department", department != null ? department : "");
        model.addAttribute("totalPages", employeePage.getTotalPages());
        model.addAttribute("totalElements", employeePage.getTotalElements());
        model.addAttribute("action", action);
        model.addAttribute("id", id);

        model.addAttribute("totalEmployeesCount", employeeService.countTotalActive());
        model.addAttribute("activeEmployeesCount", employeeService.countTotalActive());
        model.addAttribute("avgSalaryCount", employeeService.getAverageSalary());

        if ("edit".equals(action) && id != null) {
            if (!model.containsAttribute("employeeform")) {
                EmployeeResponseDto emp = employeeService.getEmployeeById(id);
                EmployeeRequestDto form = EmployeeRequestDto.builder()
                        .firstName(emp.getFirstName())
                        .lastName(emp.getLastName())
                        .email(emp.getEmail())
                        .phone(emp.getPhone())
                        .department(emp.getDepartment())
                        .position(emp.getPosition())
                        .salary(emp.getSalary())
                        .hireDate(emp.getHireDate())
                        .build();
                model.addAttribute("employeeForm", form);
            }
        } else if ("view".equals(action) && id != null) {
            EmployeeResponseDto emp = employeeService.getEmployeeById(id);
            model.addAttribute("viewEmployee", emp);
        } else if ("delete".equals(action) && id != null) {
            EmployeeResponseDto emp = employeeService.getEmployeeById(id);
            model.addAttribute("deleteEmployee", emp);
        } else if ("add".equals(action)) {
            if (!model.containsAttribute("employeeForm")) {
                model.addAttribute("employeeForm", new EmployeeRequestDto());
            }
        }

        if (!model.containsAttribute("employeeForm")) {
            model.addAttribute("employeeForm", new EmployeeRequestDto());
        }

        return "index";
    }

    @PostMapping("/employees/save")
    public String saveEmployee(
            @Valid @ModelAttribute("employeeForm") EmployeeRequestDto form,
            BindingResult bindingResult,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "department", required = false) String department,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Page<EmployeeResponseDto> employeePage = employeeService.getAllEmployees(
                    pageNo, pageSize, sortBy, sortDir, firstName, department);

            model.addAttribute("employees", employeePage.getContent());
            model.addAttribute("pageNo", pageNo);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("sortBy", sortBy);
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("firstName", firstName != null ? firstName : "");
            model.addAttribute("department", department != null ? department : "");
            model.addAttribute("totalPages", employeePage.getTotalPages());
            model.addAttribute("totalElements", employeePage.getTotalElements());
            model.addAttribute("action", id != null ? "edit" : "add");
            model.addAttribute("id", id);
            model.addAttribute("totalEmployeesCount", employeeService.countTotalActive());
            model.addAttribute("activeEmployeesCount", employeeService.countTotalActive());
            model.addAttribute("avgSalaryCount", employeeService.getAverageSalary());
            model.addAttribute("errorMessage", "Validation failed. Please correct the highlighted errors.");
            return "index";
        }

        try {
            if (id != null) {
                employeeService.updateEmployee(id, form);
                redirectAttributes.addFlashAttribute("successMessage", "Employee details updated successfully.");
            } else {
                employeeService.createEmployee(form);
                redirectAttributes.addFlashAttribute("successMessage", "New employee registered successfully.");
            }
        } catch (Exception ex) {
            bindingResult.rejectValue("email", "duplicate", ex.getMessage());

            Page<EmployeeResponseDto> employeePage = employeeService.getAllEmployees(
                    pageNo, pageSize, sortBy, sortDir, firstName, department);

            model.addAttribute("employees", employeePage.getContent());
            model.addAttribute("pageNo", pageNo);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("sortBy", sortBy);
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("firstName", firstName != null ? firstName : "");
            model.addAttribute("department", department != null ? department : "");
            model.addAttribute("totalPages", employeePage.getTotalPages());
            model.addAttribute("totalElements", employeePage.getTotalElements());
            model.addAttribute("action", id != null ? "edit" : "add");
            model.addAttribute("id", id);
            model.addAttribute("totalEmployeesCount", employeeService.countTotalActive());
            model.addAttribute("activeEmployeesCount", employeeService.countTotalActive());
            model.addAttribute("avgSalaryCount", employeeService.getAverageSalary());
            model.addAttribute("errorMessage", ex.getMessage());
            return "index";
        }

        return "redirect:/?pageNo=" + pageNo
                + "&pageSize=" + pageSize
                + "&sortBy=" + sortBy
                + "&sortDir=" + sortDir
                + (firstName != null && !firstName.isBlank() ? "&firstName=" + firstName : "")
                + (department != null && !department.isBlank() ? "&department=" + department : "");
    }

    @PostMapping("/employees/delete/{id}")
    public String deleteEmployee(
            @PathVariable("id") Long id,
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "department", required = false) String department,
            RedirectAttributes redirectAttributes
    ) {
        employeeService.deleteEmployee(id);
        redirectAttributes.addFlashAttribute("successMessage", "Employee record soft-deleted successfully.");
        return "redirect:/?pageNo=" + pageNo
                + "&pageSize=" + pageSize
                + "&sortBy=" + sortBy
                + "&sortDir=" + sortDir
                + (firstName != null && !firstName.isBlank() ? "&firstName=" + firstName : "")
                + (department != null && !department.isBlank() ? "&department=" + department : "");
    }
}
