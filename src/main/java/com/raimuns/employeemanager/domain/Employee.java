package com.raimuns.employeemanager.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity(name = "Employee")
@NoArgsConstructor
@Table(name = "employees",
        uniqueConstraints = {
                @UniqueConstraint(name = "employee_email_unique", columnNames = "email")
        })
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(
            name = "email",
            nullable = false
    )
    private String email;
    private LocalDate dob;
    @Enumerated
    private JobTitle jobTitle;
    private String phone;
    private String imageUrl;

    @Column(nullable = false, updatable = false)
    private String employeeCode;

    public Employee(EmployeeDto employeeDto) {
        setName(employeeDto.getName());
        setEmail(employeeDto.getEmail());
        setDob(LocalDate.parse(employeeDto.getDob()));
        setJobTitle(JobTitle.valueOf(employeeDto.getJobTitle()));
        setPhone(employeeDto.getPhone());
        setImageUrl(employeeDto.getImageUrl());
        setEmployeeCode(employeeDto.getEmployeeCode());
    }

    public void updateEmployee(EmployeeDto employeeDto) {
        setName(employeeDto.getName());
        setEmail(employeeDto.getEmail());
        setDob(LocalDate.parse(employeeDto.getDob()));
        setJobTitle(JobTitle.valueOf(employeeDto.getJobTitle()));
        setPhone(employeeDto.getPhone());
        setImageUrl(employeeDto.getImageUrl());
    }
}
