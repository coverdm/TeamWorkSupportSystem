package com.matuszak.engineer.domain.project.model.entity;

import com.matuszak.engineer.domain.project.exceptions.WorkerAlreadyHiredException;
import com.matuszak.engineer.domain.project.model.*;
import com.matuszak.engineer.domain.project.model.dto.TaskDto;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
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
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
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

    public Worker hireWorker(String userId, ProjectRole projectRole) throws WorkerAlreadyHiredException {

        if(isHiredInProject(userId)){
            throw new WorkerAlreadyHiredException("Worker " + userId + " is already hired");
        }

        Worker worker = Worker.builder()
                .workerId(new WorkerId(userId))
                .projectRole(projectRole)
                .build();

        this.workers.add(worker);

        return worker;
    }

    private boolean isHiredInProject(String userId) {
        return this.workers
                .stream()
                .map(Worker::getWorkerId)
                .anyMatch(w -> w.equals(userId));
    }

    public Task createTask(TaskDto taskDto){

        Task task = Task.builder()
                .taskId(new TaskId(TaskId.generateShortId()))
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .workers(taskDto.getWorkers())
                .difficult(taskDto.getDifficult())
                .deadline(taskDto.getDeadline())
                .created(Timestamp.from(Instant.now()))
                .build();

        if (task.getWorkers().isEmpty()) {
            task.setStatus(TaskStatus.TODO);
        } else {
            task.setStatus(TaskStatus.IN_PROGRESS);
        }

        this.tasks.add(task);

        return task;
    }

    public void addSourceCode(SourceCode sourceCode){
        this.sourceCode.add(sourceCode);
    }

    public void finish() {
        this.projectStatus = ProjectStatus.DELETED;
    }

    public void close() {
        this.projectStatus = ProjectStatus.CLOSED;
    }

    public void removeTask(TaskId taskId){
        this.tasks.stream()
                .filter(task -> task.getTaskId().getTaskId().equals(taskId.getTaskId()))
                .findAny()
                .ifPresent(this.tasks::remove);
    }

    public void layOffWorker(Worker worker) {
        this.workers.remove(worker);
    }

    private Project() { // just for hibernate
    }
}