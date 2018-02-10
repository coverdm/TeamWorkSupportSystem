package com.matuszak.engineer.project.boundary.web;

import com.matuszak.engineer.project.exceptions.ProjectNotFoundException;
import com.matuszak.engineer.project.exceptions.TaskNotFoundException;
import com.matuszak.engineer.project.model.ProjectId;
import com.matuszak.engineer.project.model.TaskId;
import com.matuszak.engineer.project.model.dto.TaskDto;
import com.matuszak.engineer.project.model.dto.WorkerDto;
import com.matuszak.engineer.project.service.ProjectFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Log
public class TaskController {

    private final ProjectFacade projectFacade;

    @GetMapping("/{uuid}/task")
    public ResponseEntity<TaskDto> getTask(@PathVariable String uuid, @RequestParam String taskId){
        try{
            TaskDto task = projectFacade.getTask(new ProjectId(uuid), new TaskId(taskId));
            return new ResponseEntity<>(task, HttpStatus.OK);
        }catch (TaskNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{uuid}/tasks")
    public ResponseEntity<Collection<TaskDto>> getTasks(@PathVariable String uuid){
        Collection<TaskDto> tasks = this.projectFacade.getTasks(new ProjectId(uuid));
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping("/{uuid}/task/create")
    public ResponseEntity<TaskDto>  createTask(@PathVariable String uuid, @RequestBody TaskDto taskDto) {

        log.info(taskDto.toString());

        TaskDto task = this.projectFacade.createTask(new ProjectId(uuid), taskDto);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}/task/delete")
    public ResponseEntity deleteTask(@PathVariable String uuid, @RequestParam String taskId) {
        this.projectFacade.removeTask(new ProjectId(uuid), new TaskId(taskId));
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{uuid}/task/{taskId}/complete")
    public ResponseEntity completeTask(@PathVariable String uuid, @PathVariable String taskId){
        try {
            this.projectFacade.completeTask(new ProjectId(uuid), new TaskId(taskId));
            return new ResponseEntity(HttpStatus.OK);
        }catch (ProjectNotFoundException | TaskNotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{uuid}/task/update")
    public ResponseEntity updateTask(@PathVariable String uuid, @RequestBody TaskDto taskDto){
        try {
            this.projectFacade.updateTask(new ProjectId(uuid), taskDto);
            return new ResponseEntity(HttpStatus.OK);
        }catch (ProjectNotFoundException | TaskNotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}