package com.matuszak.projects.auth.util;

import com.matuszak.projects.auth.annotations.ValidEmail;
import lombok.extern.java.Log;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

@Log
public class CustomEmailValidator implements ConstraintValidator<ValidEmail, String>{

    private final String EMAIL_PATTERN = "[a-zA-Z1-9_.]+[@]{1}[a-zA-Z1-9.]+[.]{1}[a-zA-Z1-9]{2,}";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Override
    public void initialize(ValidEmail validEmail) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return pattern.matcher(email).matches();
    }
}
