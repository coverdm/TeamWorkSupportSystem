package com.matuszak.engineer.boundary.web;

import com.matuszak.engineer.domain.project.model.*;
import com.matuszak.engineer.domain.project.model.dto.TaskDTO;
import com.matuszak.engineer.domain.project.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log
@RequestMapping("/api/project/{projectId}/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/getAll")
    public ResponseEntity<Collection<TaskDTO>> getParticipantsTasks(@PathVariable String projectId,
                                                                    @RequestParam String participantId){

        List<TaskDTO> participantsTask = taskService
                .getAllAvailableParticipantTasks(new ProjectId(projectId), new ParticipantId(participantId));

        if(participantsTask.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(participantsTask, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@PathVariable String projectId,
                                              @RequestBody TaskProperties taskProperties){

        log.info(taskProperties.toString());

        TaskDTO taskDTO = taskService.createTask(new ProjectId(projectId), taskProperties);
        return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
    }

    @PutMapping("/finish")
    public ResponseEntity<TaskDTO> finishTask(@PathVariable String projectId,
                                     @RequestParam String taskId){

        TaskDTO taskDTO = taskService.finishTask(new ProjectId(projectId), new TaskId(taskId));

        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }
}