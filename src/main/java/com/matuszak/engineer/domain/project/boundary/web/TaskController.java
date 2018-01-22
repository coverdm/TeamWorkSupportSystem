package com.matuszak.engineer.domain.project.boundary.web;

import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.TaskId;
import com.matuszak.engineer.domain.project.model.dto.TaskDto;
import com.matuszak.engineer.domain.project.model.dto.WorkerDto;
import com.matuszak.engineer.domain.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Log
@RequestMapping("/api/project")
public class TaskController {

    private final ProjectService projectService;

    @GetMapping("/{uuid}/tasks")
    public ResponseEntity<Collection<TaskDto>> getTasks(@PathVariable String uuid){
        Collection<TaskDto> tasks = this.projectService.getTasks(new ProjectId(uuid));
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping("/{uuid}/task/create")
    public ResponseEntity<TaskDto>  createTask(@PathVariable String uuid, @RequestBody TaskDto taskDto) {
        TaskDto task = this.projectService.createTask(new ProjectId(uuid), taskDto);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}/task/delete")
    public ResponseEntity deleteTask(@PathVariable String uuid, @RequestParam String taskId) {
        this.projectService.removeTask(new ProjectId(uuid), new TaskId(taskId));
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{uuid}/task/workers")
    public ResponseEntity addWorkers(@PathVariable String uuid, @RequestParam String taskId, @RequestBody Collection<WorkerDto> workers){
        this.projectService.addWorkersToTask(new ProjectId(uuid), new TaskId(taskId), workers);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{uuid}/task/update")
    public ResponseEntity updateTask(@PathVariable String uuid, @RequestBody TaskDto taskDto) {
        this.projectService.updateTask(new ProjectId(uuid), taskDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}