package com.matuszak.engineer.project.model.entity;

import com.matuszak.engineer.project.exceptions.IssueRoomNotFoundException;
import com.matuszak.engineer.project.exceptions.TaskNotFoundException;
import com.matuszak.engineer.project.exceptions.WorkerAlreadyHiredException;
import com.matuszak.engineer.project.model.*;
import com.matuszak.engineer.project.model.dto.IssueRoomProperties;
import com.matuszak.engineer.project.model.dto.SourceCodeDto;
import com.matuszak.engineer.project.model.dto.TaskDto;
import com.mongodb.BasicDBObject;
import lombok.Data;
import lombok.ToString;
import lombok.extern.java.Log;
import org.bson.types.BSONTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Data
@Log
@Document
@ToString
public class Project{

    @Id
    private ProjectId projectId;
    private Owner ownerId;
    private ProjectProperties projectProperties;
    private ProjectStatus projectStatus;
    private Collection<Worker> workers;
    private SourceCode sourceCode;
    private Collection<Task> tasks;
    private Collection<IssueRoom> issueRooms;

    public Project(ProjectId projectId, ProjectProperties projectProperties) {
        this.projectId = projectId;
        this.projectProperties = projectProperties;
        projectStatus = ProjectStatus.CREATED;
        this.workers = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.issueRooms = new ArrayList<>();
        this.sourceCode = new SourceCode(SourceCodeRepositoryHolderType.GITHUB, new SourceCodeHolderLink(""));
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
                .created(String.valueOf(Timestamp.from(Instant.now()).getTime()))
                .deadline(taskDto.getDeadline())
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

    public IssueRoom createIssueRoom(IssueRoomProperties issueRoomProperties) {

        IssueRoom issueRoom = IssueRoom.builder()
                .issueRoomId(new IssueRoomId(IssueRoomId.generate()))
                .title(issueRoomProperties.getTitle())
                .question(new Question(issueRoomProperties.getQuestion().getAuthor(), issueRoomProperties.getQuestion().getMessage()))
                .answers(new ArrayList<>())
                .questionStatus(QuestionStatus.ACTIVE)
                .build();

        log.info(issueRoom.toString());

        this.issueRooms.add(issueRoom);

        return issueRoom;
    }

    public IssueRoom getIssueRoom(IssueRoomId issueRoomId){
        return this.issueRooms.stream()
                .filter(is -> is.getIssueRoomId().equals(issueRoomId.getId()))
                .findAny()
                .orElseThrow(IssueRoomNotFoundException::new);
    }

    public void closeIssuesRoom(IssueRoomId issueRoomId){
        this.getIssueRoom(issueRoomId)
                .closeAsSolved();
    }

    public void addSourceCode(SourceCodeDto sourceCodeDto){
        this.sourceCode = SourceCode.builder()
                .sourceCodeHolderLink(sourceCodeDto.getSourceCodeHolderLink())
                .sourceCodeRepositoryHolderType(sourceCodeDto.getSourceCodeRepositoryHolderType())
                .build();
    }
}