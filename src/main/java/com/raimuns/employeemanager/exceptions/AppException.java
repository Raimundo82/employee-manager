package com.raimuns.employeemanager.exceptions;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public AppException(ErrorMessage errorMessage) {
        super(errorMessage.label);
        this.errorMessage = errorMessage;
    }

    public AppException(ErrorMessage errorMessage, String value) {
        super(String.format(errorMessage.label, value));
        this.errorMessage = errorMessage;
    }

    public AppException(ErrorMessage errorMessage, Long value) {
        super(String.format(errorMessage.label, value));
        this.errorMessage = errorMessage;
    }
}
