package com.matuszak.engineer.domain.project.model.entity;

import com.matuszak.engineer.domain.project.exceptions.TaskNotFoundException;
import com.matuszak.engineer.domain.project.exceptions.WorkerAlreadyHiredException;
import com.matuszak.engineer.domain.project.model.*;
import com.matuszak.engineer.domain.project.model.dto.IssueRoomProperties;
import com.matuszak.engineer.domain.project.model.dto.TaskDto;
import lombok.Data;
import lombok.ToString;
import lombok.extern.java.Log;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Data
@Log
@Entity
@ToString
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

    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Collection<IssueRoom> issueRooms;

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
                .difficult(taskDto.getDifficult())
                .workers(taskDto.getWorkers())
                .deadline(taskDto.getDeadline())
                .created(Timestamp.from(Instant.now()))
                .build();

        if (task.getWorkers() == null || task.getWorkers().isEmpty())
            task.setStatus(TaskStatus.TODO);
        else
            task.setStatus(TaskStatus.IN_PROGRESS);

        this.tasks.add(task);

        return task;
    }

    public void updateTask(TaskDto taskDto) throws TaskNotFoundException{

        Task task = this.tasks.stream()
                .filter(t -> t.getTaskId().getTaskId().equals(taskDto.getTaskId()))
                .findAny()
                .orElseThrow(TaskNotFoundException::new);

        if(taskDto.getDeadline() != null){
            task.setDeadline(taskDto.getDeadline());
        }
        if(taskDto.getDifficult() != null){
            task.setDifficult(taskDto.getDifficult());
        }
        if(taskDto.getWorkers() != null){
            task.setWorkers(taskDto.getWorkers());
            task.setStatus(TaskStatus.IN_PROGRESS);
        }else{
            task.setStatus(TaskStatus.TODO);
        }
        if(taskDto.getDescription() != null){
            task.setDescription(taskDto.getDescription());
        }
        if(taskDto.getTitle() != null){
            task.setTitle(taskDto.getTitle());
        }
    }

    public void completeTask(String taskId) throws TaskNotFoundException{

        this.tasks.stream()
                .filter(t -> t.getTaskId().getTaskId().equals(taskId))
                .findAny()
                .orElseThrow(TaskNotFoundException::new)
                .complete();
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

    public Map dashboard(){

        HashMap<String, Object> dashboard = new HashMap<>();
        dashboard.put("tasks", getMapSortedTaskByStatus());
        dashboard.put("workers", this.workers.size());
        dashboard.put("status", this.projectStatus);
        dashboard.put("properties", this.projectProperties);

        return dashboard;
    }

    private Map getMapSortedTaskByStatus(){

        HashMap<String, Long> tasks = new HashMap<>();

        long todo = this.tasks.stream()
                .filter(task -> task.getStatus().equals(TaskStatus.TODO))
                .count();

        tasks.put("todo", todo);

        long in_progress = this.tasks.stream()
                .filter(task -> task.getStatus().equals(TaskStatus.IN_PROGRESS))
                .count();

        tasks.put("in_progress", in_progress);

        long finished = this.tasks.stream()
                .filter(task -> task.getStatus().equals(TaskStatus.FINISHED))
                .count();

        tasks.put("finished", finished);

        return tasks;
    }

    private Project() { // just for hibernate
    }

    public IssueRoom createIssueRoom(IssueRoomProperties issueRoomProperties) {

        IssueRoom issueRoom = IssueRoom.builder()
                .question(new Question(issueRoomProperties.getQuestion().getAuthor(), issueRoomProperties.getQuestion().getMessage()))
                .answerCollection(new ArrayList<>())
                .build();

        this.issueRooms.add(issueRoom);

        return issueRoom;
    }
}