package com.matuszak.projects.auth.util;

import com.matuszak.projects.auth.annotations.ValidEmail;
import lombok.extern.java.Log;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

@Log
public class CustomEmailValidator implements ConstraintValidator<ValidEmail, String>{

    //TODO create email pattern by using regular expression (REGEX)
    private final String EMAIL_PATTERN = "";

    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Override
    public void initialize(ValidEmail validEmail) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return pattern.matcher(email).matches();
    }
}
