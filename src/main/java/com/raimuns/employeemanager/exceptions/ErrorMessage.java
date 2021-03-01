package com.raimuns.employeemanager.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorMessage {

    EMPLOYEE_NOT_FOUND_ID("Employee with id: %s not found"),
    EMPLOYEE_NAME_INVALID("Employee name %s is invalid"),
    EMAIL_ALREADY_EXISTS("Email %s already exists"),
    EMPLOYEE_IS_NOT_ADULT("Employee is not adult"),
    PHONE_NUMBER_INVALID("Phone number %s is invalid");


    public final String label;

}
