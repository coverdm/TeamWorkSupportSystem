package com.matuszak.projects.controllers;

import com.matuszak.projects.domain.User;
import com.matuszak.projects.repositories.UserRepository;
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
@RequestMapping("/rest/userManagement")
public class UserManagementController {

   private UserRepository userRepository;

   @Autowired
    public UserManagementController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String getHello(){
       return "Hello world";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    @CrossOrigin(origins = "localhost:4200")
    public ResponseEntity<List<User>> userList(){

        List<User> all = userRepository.findAll();

        if(all.size() == 0)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    @CrossOrigin(origins = "localhost:4200")
    public ResponseEntity<?> updateUser(@RequestBody User user){
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
