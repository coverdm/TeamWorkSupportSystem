package com.matuszak.projects.controllers;

import com.matuszak.projects.domain.Project;
import com.matuszak.projects.repositories.UserRepository;
import com.matuszak.projects.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.logging.Logger;

/**
 * Created by dawid on 06.04.17.
 */
@RestController
@RequestMapping("/rest/project")
public class MainController {

    private ProjectService projectService;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public MainController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping(value = "/create")
    public void createProject(@RequestBody Project project){
        projectService.createProject(project);
    }

    @GetMapping("/test")
    public String loggedUsers(){
        return "TROLLO DONE";
    }

}
