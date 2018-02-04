package com.matuszak.engineer.auth.validator;

import com.matuszak.engineer.auth.annotations.ValidEmail;
import com.matuszak.engineer.auth.exceptions.IllegalEmailFormatException;
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

        if(!pattern.matcher(email).matches())
            throw new IllegalEmailFormatException();

        return true;
    }
}
