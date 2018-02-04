package com.matuszak.engineer.project.annotation.impl;

import com.matuszak.engineer.project.annotation.CascadeSave;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

@RequiredArgsConstructor
public abstract class CascadingMongoEventListener extends AbstractMongoEventListener<Object> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        ReflectionUtils.doWithFields(source.getClass(), field -> {
            ReflectionUtils.makeAccessible(field);
            if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
                final Object fieldValue = field.get(source);
                if(fieldValue instanceof List<?>){
                    for (Object item : (List<?>)fieldValue){
                        checkField(item);
                    }
                }else{
                    checkField(fieldValue);
                }
            }
        });
    }

    private void checkField(Object fieldValue) {
        DbRefFieldCallback callback = new DbRefFieldCallback();
        if (fieldValue != null) {
            ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
            if (!callback.isIdFound()) {
                throw new MappingException("Cannot perform cascade save on child object without id set");
            }
            updateEntity(fieldValue);
        }
    }

    protected abstract void updateEntity(Object fieldValue);
}
