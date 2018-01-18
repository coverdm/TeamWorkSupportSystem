package com.matuszak.engineer.domain.project.model.entity;

import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.ProjectProperties;
import com.matuszak.engineer.domain.project.model.ProjectStatus;
import com.matuszak.engineer.domain.project.model.TaskId;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
public class Project{

    @EmbeddedId
    private ProjectId projectId;
    private Owner ownerId;

    private ProjectProperties projectProperties;

    @Enumerated(EnumType.ORDINAL)
    private ProjectStatus projectStatus;

    @OneToMany
    private Collection<Worker> workers;

    @OneToMany
    private Collection<SourceCode> sourceCode;

    @OneToMany
    private Collection<Task> tasks;

    public Project(ProjectId projectId, ProjectProperties projectProperties) {
        this.projectId = projectId;
        this.projectProperties = projectProperties;
        projectStatus = ProjectStatus.CREATED;
        this.workers = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    public void addWorker(Worker worker){
        workers.add(worker);
    }

    public void addTask(Task task){
        this.tasks.add(task);
    }

    public void addSourceCode(SourceCode sourceCode){
        this.sourceCode.add(sourceCode);
    }

    public void markAsDeleted() {
        this.projectStatus = ProjectStatus.DELETED;
    }

    public void markAsClosed() {
        this.projectStatus = ProjectStatus.CLOSED;
    }

    public void removeTask(TaskId taskId){
        this.tasks.stream()
                .filter(task -> task.getTaskId().getTaskId().equals(taskId.getTaskId()))
                .findAny()
                .ifPresent(this.tasks::remove);
    }

    public void removeWorker(Worker worker) {
        this.workers.remove(worker);
    }

    private Project() { // just for hibernate
    }
}