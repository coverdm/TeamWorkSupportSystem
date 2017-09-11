package com.matuszak.projects.project.controller;

import com.matuszak.projects.project.entity.Project;
import com.matuszak.projects.project.service.ProjectManager;
import com.matuszak.projects.project.service.ProjectPersistence;
import com.matuszak.projects.project.entity.ProjectStatus;
import com.matuszak.projects.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@Log
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectPersistence projectPersistence;
    private final ProjectManager projectManager;

    @PostMapping("/create")
    public ResponseEntity<Project> createProject(@RequestBody Project project){
        return new ResponseEntity<>(projectManager.create(project),HttpStatus.ACCEPTED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Project> getProject(@PathVariable("uuid") String uuid){
        return new ResponseEntity<>(projectPersistence.getProjectByUUID(uuid), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAllProjects")
    public ResponseEntity<List<Project>> getAllProjects(){
        return new ResponseEntity<>(this.projectPersistence.getProjects(), HttpStatus.ACCEPTED);
    }

    @PostMapping("/{uuid}/addParticipants")
    public ResponseEntity<?> addParticipant(@RequestBody List<User> user, @PathVariable String uuid){
        projectManager.addParticipants(uuid, user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{uuid}/status")
    public ResponseEntity<Project> changeStatus(@PathVariable String uuid, @RequestBody ProjectStatus status){
        return new ResponseEntity<>(projectManager.changeStatus(uuid, status), HttpStatus.ACCEPTED);
    }
}
