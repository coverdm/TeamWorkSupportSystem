package com.matuszak.engineer.domain.project.model.entity;

import com.matuszak.engineer.domain.project.model.ProjectRole;
import com.matuszak.engineer.infrastructure.entity.UserId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Worker implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UserId userId;

    @Enumerated
    private ProjectRole projectRole;

    private Worker() { // just for hibernate
    }

    public Worker(UserId userId, ProjectRole projectRole) {
        this.userId = userId;
        this.projectRole = projectRole;
    }

    public String getUserId() {
        return userId.getUserId();
    }
}

