package com.matuszak.engineer.domain.project.model.entity;

import com.matuszak.engineer.domain.project.model.ProjectRole;
import com.matuszak.engineer.domain.project.model.WorkerId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/*
* Worker class represents real worker works with real problems.
* The class has id because one account can be more that one worker (for example: The user can be participant of two project
  * so in the other words he works in 2 projects and hes not the same worker).
* Property WorkerId represents unique AccountId
* ProjectRole means role in the project.
* */

@Entity
@Data
public class Worker implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private WorkerId workerId;

    @Enumerated
    private ProjectRole projectRole;

    private Worker() { // just for hibernate
    }

    public Worker(WorkerId workerId, ProjectRole projectRole) {
        this.workerId = workerId;
        this.projectRole = projectRole;
    }

    public String getWorkerId() {
        return workerId.getWorkerId();
    }
}

