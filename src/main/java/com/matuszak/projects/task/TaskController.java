package com.matuszak.projects.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/{projectUUID}/task")
@RestController
public class TaskController {

    private final TaskPersistenceService taskPersistenceService;

    @Autowired
    public TaskController(TaskPersistenceService taskPersistenceService) {
        this.taskPersistenceService = taskPersistenceService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<Task>> listOfTask(@PathVariable String projectUUID){
        return new ResponseEntity<>(taskPersistenceService.getTasksFromProject(projectUUID), HttpStatus.OK);
    }

    @GetMapping("/{taskID}/remove")
    public ResponseEntity<?> removeTask(@PathVariable String taskID){

        Task taskById = taskPersistenceService.getTaskById(Long.parseLong(taskID));
        taskPersistenceService.delete(taskById);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@PathVariable String projectUUID, @RequestBody Task task){
        return new ResponseEntity<>(taskPersistenceService.create(projectUUID, task), HttpStatus.CREATED);
    }
}