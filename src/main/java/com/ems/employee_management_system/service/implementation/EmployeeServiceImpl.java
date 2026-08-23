package com.ems.employee_management_system.service.implementation;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ems.employee_management_system.dto.EmployeeRequestDto;
import com.ems.employee_management_system.dto.EmployeeResponseDto;
import com.ems.employee_management_system.entity.Employee;
import com.ems.employee_management_system.exception.EmailAlreadyExistsException;
import com.ems.employee_management_system.exception.ResourceNotFoundException;
import com.ems.employee_management_system.mapper.EmployeeMapper;
import com.ems.employee_management_system.repository.EmployeeRepository;
import com.ems.employee_management_system.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    @Transactional
    public EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto) {
        if (employeeRepository.existsByEmail(employeeRequestDto.getEmail())) {
            throw new EmailAlreadyExistsException(
                String.format("email %s is already exists", employeeRequestDto.getEmail()));
        }

        Employee employee = employeeMapper.toEntity(employeeRequestDto);

        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDto(savedEmployee);
    }

    @Override
    @Transactional(readOnly=true)
    public EmployeeResponseDto getEmployeeById(Long id){
        Employee employee =employeeRepository.findByIdAndActiveTrue(id)
            .orElseThrow(()->new ResourceNotFoundException("employee", "id", id));
        return employeeMapper.toDto(employee);
    }

    @Override
    @Transactional(readOnly=true)
    public Page<EmployeeResponseDto> getAllEmployees(int pageNo, int pageSize, String sortBy, String sortDir, String firstName, String department){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageagle = PageRequest.of(pageNo, pageSize, sort);

        Page<Employee> employeePage;

        if(firstName!=null&&!firstName.isBlank() && department!=null&&!department.isBlank()){
            employeePage=employeeRepository.findByFirstNameContainingIgnoreCaseAndDepartmentContainingIgnoreCaseAndActiveTrue(firstName, department, pageagle);
        }else if(firstName!=null&&!firstName.isBlank()){
            employeePage=employeeRepository.findByFirstNameContainingIgnoreCaseAndActiveTrue(firstName, pageagle);
        }else if(department!=null&&!department.isBlank()){
            employeePage=employeeRepository.findByDepartmentContainingIgnoreCaseAndActiveTrue(department, pageagle);
        }else{
            employeePage=employeeRepository.findByActiveTrue(pageagle);
        }

        return employeePage.map(employeeMapper::toDto);
    }

    @Override
    @Transactional
    public EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto employeeRequestDto){

        Employee employee=employeeRepository.findByIdAndActiveTrue(id)
            .orElseThrow(()->new ResourceNotFoundException("employee", "id ", id));

        if (employeeRepository.existsByEmailAndIdNot(employeeRequestDto.getEmail(),id)) {
            throw new EmailAlreadyExistsException(
                String.format("email %s is already exisits", employeeRequestDto.getEmail()));
        }

        employeeMapper.updateEntityFromDto(employeeRequestDto, employee);

        return employeeMapper.toDto(employee);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id){
        Employee employee=employeeRepository.findByIdAndActiveTrue(id)
            .orElseThrow(()->new ResourceNotFoundException("employee", "id ", id));

        employee.setActive(false);
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public Long countTotalActive(){
        return employeeRepository.countByActiveTrue();
    }

    @Override
    @Transactional
    public BigDecimal getAverageSalary(){
        BigDecimal avgSalary=employeeRepository.getAverageSalaryOfActiveEmployees();
        if(avgSalary==null)     return BigDecimal.ZERO;
        return avgSalary;
    }
}
