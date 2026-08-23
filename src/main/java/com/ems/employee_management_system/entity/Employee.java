package com.ems.employee_management_system.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable=false , length=50)
    private String firstName;

    @Column(name="last_name" , nullable=false , length=50)
    private String lastName;

    @Column(name="email" , nullable=false , unique=true , length=100)
    private String email;

    @Column(name="phone" , nullable=false , length=20)
    private String phone;
    
    @Column(name="department" , nullable=false , length=100)
    private String department;

    @Column(name="position" , nullable=false , length=100)
    private String position;

    @Column(name="salary" , nullable=false , precision=12 , scale=2)
    private BigDecimal salary;

    @Column(name="hire_date" , nullable=false)
    private LocalDate hireDate;
    
    @Column(name="active" , nullable=false)
    @Builder.Default
    private boolean active = true;

    @Column(name= "created_at" , nullable=false , updatable=false)
    private LocalDateTime createdAt;

    @Column(name="updated_at" , nullable=false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        createdAt=LocalDateTime.now();
        updatedAt=LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt=LocalDateTime.now();
    }

}
