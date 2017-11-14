package com.matuszak.engineer.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseId {

    protected String id;

    public String generateId(){
        return UUID.randomUUID().toString();
    }
}
