package com.matuszak.engineer.infrastructure.entity;

import lombok.AllArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class User {

    @EmbeddedId
    protected UserId userId;

}
