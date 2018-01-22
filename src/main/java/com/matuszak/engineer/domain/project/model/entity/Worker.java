package com.matuszak.engineer.domain.project.model.entity;

import com.matuszak.engineer.domain.project.model.ProjectRole;
import com.matuszak.engineer.domain.project.model.WorkerId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/*
* Worker class represents real userId works with real problems.
* The class has id because one account can be more that one userId (for example: The user can be participant of two project
  * so in the other words he works in 2 projects and hes not the same userId).
* Property WorkerId represents unique AccountId
* ProjectRole means role in the project.
* */

@Entity
@Data
@Builder
@AllArgsConstructor
public class Worker implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private WorkerId workerId;

    @Enumerated
    private ProjectRole projectRole;

    private Worker() { // just for hibernate
    }

    public String getWorkerId() {
        return workerId.getWorkerId();
    }
}

