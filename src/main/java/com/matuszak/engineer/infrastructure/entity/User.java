package com.matuszak.engineer.infrastructure.entity;

import com.matuszak.engineer.domain.project.model.WorkerId;

import javax.persistence.*;

@MappedSuperclass
public class User {

    @EmbeddedId
    protected WorkerId workerId;

    public WorkerId getWorkerId(){
        return workerId;
    }
}
