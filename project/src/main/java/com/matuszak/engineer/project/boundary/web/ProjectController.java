package com.matuszak.engineer.project.boundary.web;

import com.matuszak.engineer.project.exceptions.ProjectNotFoundException;
import com.matuszak.engineer.project.exceptions.WorkerAlreadyHiredException;
import com.matuszak.engineer.project.interncomm.NotificationBus;
import com.matuszak.engineer.project.model.ProjectId;
import com.matuszak.engineer.project.model.ProjectProperties;
import com.matuszak.engineer.project.model.dto.*;
import com.matuszak.engineer.project.model.entity.Project;
import com.matuszak.engineer.project.service.ProjectFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Log
public class ProjectController {

    private final ProjectFacade projectFacade;
    private final ModelMapper modelMapper;
    private final NotificationBus notificationBus;

    @GetMapping("/getAll")
    public ResponseEntity<Collection<ProjectDTO>> getProjects(@RequestParam String userId) {
        Collection<ProjectDTO> projects =
                projectFacade.getProjects(userId);
        return new ResponseEntity<>(projects, HttpStatus.ACCEPTED);
    }

    @PostMapping("/create")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectProperties projectProperties,
                                                    @RequestParam String userId) {
        Project project = projectFacade.createProject(projectProperties, userId);
        ProjectDTO projectDTO = modelMapper.map(project, ProjectDTO.class);
        return new ResponseEntity<>(projectDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProjectDTO> getProject(@RequestBody String uuid) {
        try {
            return new ResponseEntity<>(projectFacade.loadProject(new ProjectId(uuid)), HttpStatus.ACCEPTED);
        } catch (ProjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{uuid}/workers/add")
    public ResponseEntity addUserToProject(@PathVariable String uuid,
                                           @RequestBody HireModel hireModel) {
        try {
            log.info(hireModel.toString());
            projectFacade.addWorker(new ProjectId(uuid), hireModel.getUserId(), hireModel.getProjectRole());
            return new ResponseEntity(HttpStatus.OK);
        } catch (WorkerAlreadyHiredException e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

    }

    @GetMapping("/{uuid}/workers")
    public ResponseEntity<Collection<WorkerDto>> getParticipants(@PathVariable String uuid) {
        Collection<WorkerDto> workers = this.projectFacade.getWorkers(new ProjectId(uuid));
        return new ResponseEntity<>(workers, HttpStatus.OK);

    }

    @GetMapping("/{uuid}/dashboard")
    public ResponseEntity<Map<String, Object>> dashboard(@PathVariable String uuid) {
        try {
            Map map = this.projectFacade.dashboard(new ProjectId(uuid));
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (ProjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{uuid}/repository")
    public ResponseEntity initRepository(@PathVariable String uuid, @RequestBody SourceCodeDto sourceCodeDto) {

        try {
            this.projectFacade.initSourceCodeRepository(new ProjectId(uuid), sourceCodeDto);


            List<String> workers = projectFacade.getWorkers(new ProjectId(uuid)).stream().map(WorkerDto::getWorkerId).collect(Collectors.toList());

            notificationBus.notify(
                    new Event(
                            new NotificationDto("Initialized new repository", "http://localhost:4200/app/project/" + uuid, "REPOSITORY_INITIALIZED"),
                            workers
                    )
            );

            return new ResponseEntity(HttpStatus.OK);
        } catch (ProjectNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{uuid}/repository")
    public ResponseEntity<SourceCodeDto> getRepository(@PathVariable String uuid) {

        try {
            SourceCodeDto sourceCodeDto = this.projectFacade.getRepository(new ProjectId(uuid));
            return new ResponseEntity<>(sourceCodeDto, HttpStatus.OK);
        } catch (ProjectNotFoundException | NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}