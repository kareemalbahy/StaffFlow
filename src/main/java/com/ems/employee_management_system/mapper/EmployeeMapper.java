package com.ems.employee_management_system.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.ems.employee_management_system.dto.EmployeeRequestDto;
import com.ems.employee_management_system.dto.EmployeeResponseDto;
import com.ems.employee_management_system.entity.Employee;

@Component
public class EmployeeMapper {
    private final ModelMapper modelMapper;

    public EmployeeMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public Employee toEntity(EmployeeRequestDto dto){
        return modelMapper.map(dto, Employee.class);
    }

    public EmployeeResponseDto toDto(Employee employee){
        return modelMapper.map(employee, EmployeeResponseDto.class);
    }

    public void updateEntityFromDto(EmployeeRequestDto dto , Employee employee){
        modelMapper.map(dto, employee);
    }
}
