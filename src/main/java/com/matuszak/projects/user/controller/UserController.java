package com.matuszak.projects.user.controller;

import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.user.dto.UserDTO;
import com.matuszak.projects.user.service.UserPersistence;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;

@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserPersistence userPersistence;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(final UserPersistence userPersistence, ModelMapper modelMapper) {
        this.userPersistence = userPersistence;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/details")
    public ResponseEntity<?> getUserDetails(Principal principal) {
        User user = userPersistence.getUserByUsername(principal.getName());

        UserDTO map = modelMapper.map(user, UserDTO.class);

        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(User user){
        userPersistence.saveUser(user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/detele")
    public ResponseEntity<?> deleteUser(User user){
        userPersistence.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(User user){
        userPersistence.saveUser(user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userPersistence.getAllUsers(), HttpStatus.ACCEPTED);
    }
}