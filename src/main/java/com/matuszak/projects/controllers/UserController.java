package com.matuszak.projects.controllers;

import com.matuszak.projects.domain.Project;
import com.matuszak.projects.domain.User;
import com.matuszak.projects.repositories.ProjectRepository;
import com.matuszak.projects.repositories.UserRepository;
import com.matuszak.projects.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dawid on 18.06.17.
 */

@RequestMapping("/api/user")
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/details")
    public ResponseEntity<User> getUserDetails(Principal principal) {
        return new ResponseEntity<User>(userService.getUserByUsername(principal.getName()), HttpStatus.ACCEPTED);
    }
}
