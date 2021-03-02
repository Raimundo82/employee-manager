package com.raimuns.employeemanager.domain;


import com.raimuns.employeemanager.exceptions.AppException;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

import static com.raimuns.employeemanager.exceptions.ErrorMessage.JOB_TITLE_INVALID;

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
        matchJobTitle(employeeDto.getJobTitle());
        setPhone(employeeDto.getPhone());
        setImageUrl(employeeDto.getImageUrl());
        setEmployeeCode(employeeDto.getEmployeeCode());
    }

    public void updateEmployee(EmployeeDto employeeDto) {
        setName(employeeDto.getName());
        setEmail(employeeDto.getEmail());
        setDob(LocalDate.parse(employeeDto.getDob()));
        matchJobTitle(employeeDto.getJobTitle());
        setPhone(employeeDto.getPhone());
        setImageUrl(employeeDto.getImageUrl());
    }

    private void matchJobTitle(String jobTitle) {

        switch (jobTitle.toLowerCase()) {
            case "ceo" -> setJobTitle(JobTitle.CEO);
            case "designer" -> setJobTitle(JobTitle.DESIGNER);
            case "developer" -> setJobTitle(JobTitle.DEVELOPER);
            case "project manager" -> setJobTitle(JobTitle.PROJECT_MANAGER);
            default -> throw new AppException(JOB_TITLE_INVALID, jobTitle);
        }
    }
}
