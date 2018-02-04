package com.matuszak.engineer.project.annotation.impl;

import org.springframework.data.annotation.Id;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class DbRefFieldCallback implements ReflectionUtils.FieldCallback {

    private boolean idFound;

    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
        ReflectionUtils.makeAccessible(field);

        if (field.isAnnotationPresent(Id.class)) {
            idFound = true;
        }
    }

    public boolean isIdFound() {
        return idFound;
    }

}
