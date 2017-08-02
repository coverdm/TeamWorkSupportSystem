package com.matuszak.projects.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/{projectUUID}/task")
@RestController
public class TaskController {

    private final TaskPersistence taskPersistence;

    @Autowired
    public TaskController(TaskPersistence taskPersistence) {
        this.taskPersistence = taskPersistence;
    }

    @GetMapping("/get")
    public ResponseEntity<List<Task>> listOfTask(@PathVariable String projectUUID){
        return new ResponseEntity<>(taskPersistence.getTasksFromProject(projectUUID), HttpStatus.OK);
    }

    @GetMapping("/{taskID}/remove")
    public ResponseEntity<?> removeTask(@PathVariable String taskID){

        Task taskById = taskPersistence.getTaskById(Long.parseLong(taskID));
        taskPersistence.delete(taskById);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@PathVariable String projectUUID, @RequestBody Task task){
        return new ResponseEntity<>(taskPersistence.create(projectUUID, task), HttpStatus.CREATED);
    }
}