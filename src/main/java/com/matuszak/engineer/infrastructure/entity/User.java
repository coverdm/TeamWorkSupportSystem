package com.matuszak.engineer.infrastructure.entity;

import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@MappedSuperclass
public class User {

    @EmbeddedId
    protected UserId userId;

    public UserId getUserId(){
        return userId;
    }
}
