package com.raimuns.employeemanager.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeDto {

    private Long id;
    private String name;
    private String email;
    private String dob;
    private String jobTitle;
    private String phone;
    private String imageUrl;
    private String employeeCode;

    public EmployeeDto(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.email = employee.getEmail();
        this.dob = employee.getDob().toString();
        this.jobTitle = employee.getJobTitle().toString();
        this.phone = employee.getPhone();
        this.imageUrl = employee.getImageUrl();
        this.employeeCode = employee.getEmployeeCode();
    }

}
