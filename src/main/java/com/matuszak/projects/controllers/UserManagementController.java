package com.matuszak.projects.controllers;

import com.matuszak.projects.domain.User;
import com.matuszak.projects.repositories.UserRepository;
import com.matuszak.projects.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by dawid on 20.05.17.
 */
@RestController
@RequestMapping("/api/userManagement")
public class UserManagementController {

    private UserService userService;

    @Autowired
    public UserManagementController(final UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> userList() {

        List<User> all = userService.getAllUsers();

        if (all.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
