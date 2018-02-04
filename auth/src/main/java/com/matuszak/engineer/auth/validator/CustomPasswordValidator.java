package com.matuszak.engineer.auth.validator;

import com.matuszak.engineer.auth.annotations.ValidPassword;
import com.matuszak.engineer.auth.exceptions.IllegalPasswordFormatException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CustomPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private final String EMAIL_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[1-9]).{3,20}$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public boolean validateRawPassword(String rawPassword){

        if(!pattern.matcher(rawPassword).matches())
            throw new IllegalPasswordFormatException();

        return true;
    }

    @Override
    public void initialize(ValidPassword validPassword) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return validateRawPassword(s);
    }

}
