package com.matuszak.projects.project;

import com.matuszak.projects.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectPersistence projectPersistence;
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public ProjectController(ProjectPersistence projectPersistence) {
        this.projectPersistence = projectPersistence;
    }

    @PostMapping("/create")
    public ResponseEntity<Project> createProject(@RequestBody Project project){
        return new ResponseEntity<>(this.projectPersistence.saveProject(project),HttpStatus.ACCEPTED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Project> getProject(@PathVariable("uuid") String uuid){
        return new ResponseEntity<>(projectPersistence.getProjectByUUID(uuid), HttpStatus.ACCEPTED);
    }

    @PostMapping("/{uuid}/addParticipants")
    public ResponseEntity<?> addParticipant(@RequestBody List<User> user, @PathVariable String uuid){
        projectPersistence.addParticipants(uuid, user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAllProjects")
    public ResponseEntity<List<Project>> getAllProjects(){
        return new ResponseEntity<>(this.projectPersistence.getProjects(), HttpStatus.ACCEPTED);
    }
}
