package com.ems.StaffFlow.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ems.StaffFlow.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>, JpaSpecificationExecutor<Employee> {

    Optional<Employee> findByIdAndActiveTrue(Long id);

    Page<Employee> findByActiveTrue(Pageable pageable);

    Page<Employee> findByFirstNameContainingIgnoreCaseAndActiveTrue(String firstName , Pageable pageable);

    Page<Employee> findByDepartmentContainingIgnoreCaseAndActiveTrue(String department , Pageable pageable);

    Page<Employee> findByFirstNameContainingIgnoreCaseAndDepartmentContainingIgnoreCaseAndActiveTrue(
        String firstName, String department, Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Long countByActiveTrue();

    @Query("SELECT COALESCE(AVG(e.salary), 0) FROM Employee e WHERE e.active = true")
    BigDecimal getAverageSalaryOfActiveEmployees();
}
