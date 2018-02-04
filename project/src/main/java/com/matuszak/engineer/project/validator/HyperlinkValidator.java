package com.matuszak.engineer.project.validator;

import com.matuszak.engineer.project.exceptions.InvalidHyperlinkException;

public class HyperlinkValidator {

    public static void isValueValid(String valid){
        if(!valid.contains("http://") || !valid.contains("https://"))
            throw new InvalidHyperlinkException("Link has not http or https");
    }

}
