package com.raimuns.employeemanager;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Component
public class StringPatternValidator {

    private static final Predicate<String> IS_EMAIL_VALID =
            Pattern.compile(
                    "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE
            ).asPredicate();

    private static final Predicate<String> IS_DOB_VALID =
            Pattern.compile(
                    "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))",
                    Pattern.CASE_INSENSITIVE
            ).asPredicate();


    public boolean isEmailValid(String email) {
        return IS_EMAIL_VALID.test(email);
    }

    public boolean isDobValid(String dob) {
        return IS_DOB_VALID.test(dob);
    }

}

