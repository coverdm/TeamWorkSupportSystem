package com.matuszak.projects.task.controller;

import com.matuszak.projects.task.service.TaskPersistence;
import com.matuszak.projects.task.entity.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/{projectUUID}/task")
@RestController
@RequiredArgsConstructor
@Log
public class TaskController {

    private final TaskPersistence taskPersistence;

    @GetMapping("/get")
    public ResponseEntity<List<Task>> listOfTask(@PathVariable String projectUUID){
        return new ResponseEntity<>(taskPersistence.getTasksFromProject(projectUUID), HttpStatus.OK);
    }

    @GetMapping("/{taskID}/remove")
    public ResponseEntity<?> removeTask(@PathVariable String taskID){

        taskPersistence.getTaskById(Long.parseLong(taskID))
                .ifPresent(e -> taskPersistence.delete(e));

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@PathVariable String projectUUID, @RequestBody Task task){
        return new ResponseEntity<>(taskPersistence.create(projectUUID, task), HttpStatus.CREATED);
    }
}