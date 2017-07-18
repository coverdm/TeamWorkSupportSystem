package com.matuszak.projects.project;

import com.matuszak.projects.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by dawid on 26.06.17.
 */
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public ResponseEntity<Project> createProject(@RequestBody Project project){
        return new ResponseEntity<>(this.projectService.saveProject(project),HttpStatus.ACCEPTED);
    }

    @PostMapping("/{uuid}/addParticipants")
    public ResponseEntity<?> addParticipant(@RequestBody List<User> user, @PathVariable String uuid){
        projectService.addParticipants(uuid, user);
        return new ResponseEntity<Object>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Project> getProject(@PathVariable("uuid") String uuid){
        return new ResponseEntity<Project>(projectService.getProjectByUUID(uuid).get(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAllProjects")
    public ResponseEntity<List<Project>> getAllProjects(){
        return new ResponseEntity<List<Project>>(this.projectService.getProjects(), HttpStatus.ACCEPTED);
    }
}
