package com.matuszak.engineer.domain.project.model.entity;

import com.matuszak.engineer.domain.project.model.TaskId;
import com.matuszak.engineer.domain.project.model.TaskProperties;
import com.matuszak.engineer.domain.project.model.TaskStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
public class Task {

    @EmbeddedId
    private TaskId taskId;

    private TaskProperties taskProperties;

    @Enumerated
    private TaskStatus taskStatus;

    @OneToMany
    private Collection<Participant> executors;

    public Task(TaskProperties taskProperties) {
        this.taskId = new TaskId();
        this.taskProperties = taskProperties;
        this.taskStatus = TaskStatus.CREATED;
        this.executors = new ArrayList<>();
    }

    public void addExecutor(Participant participant){
        executors.add(participant);
        taskStatus = TaskStatus.IN_PROCESS;
    }

    public void markAsCanceled(){
        this.taskStatus = TaskStatus.CANCELED;
    }

    public void markAsDone(){
        this.taskStatus = TaskStatus.DONE;
    }

    private Task(){ // just for hibernate
    }
}