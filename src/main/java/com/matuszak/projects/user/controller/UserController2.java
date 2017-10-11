package com.matuszak.projects.user.controller;

import com.matuszak.projects.user.dto.UserDTO;
import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.user.exceptions.UserNotFoundException;
import com.matuszak.projects.user.service.UserPersistence;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Log
public class UserController2 {

    private final UserPersistence userPersistence;
    private final ModelMapper modelMapper;

    @GetMapping("/details")
    public ResponseEntity<?> getUserDetails(Principal principal) {

        Optional<User> userByUsername = userPersistence.getUserByUsername(principal.getName());
        userByUsername.ifPresent(user -> modelMapper.map(user, new UserDTO()));
        return new ResponseEntity<>(userByUsername
                .orElseThrow(() -> new UserNotFoundException("")), HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(UserDTO user){
//        userPersistence.registerNewUser(user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/detele")
    public ResponseEntity<?> deleteUser(UserDTO user){
//        userPersistence.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(UserDTO user){
//        userPersistence.registerNewUser(user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
