package com.matuszak.engineer.boundary.web;

import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.ProjectProperties;
import com.matuszak.engineer.domain.project.model.entity.Project;
import com.matuszak.engineer.domain.project.exceptions.ProjectNotFoundException;
import com.matuszak.engineer.domain.project.model.dto.ProjectDTO;
import com.matuszak.engineer.domain.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Log
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;
    private final ModelMapper modelMapper;

    @PostMapping("/create")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectProperties projectProperties, Principal principal){

        Project projectCreated = projectService.createProject(projectProperties);
        ProjectDTO map = modelMapper.map(projectCreated, ProjectDTO.class);

        return new ResponseEntity<>(map,HttpStatus.ACCEPTED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProjectDTO> getProject(@RequestBody ProjectId projectId){
        return new ResponseEntity<>(projectService.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new), HttpStatus.ACCEPTED);
    }
}
