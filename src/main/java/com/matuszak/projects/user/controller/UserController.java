package com.matuszak.projects.user.controller;

import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.user.dto.UserDTO;
import com.matuszak.projects.user.exceptions.UserNotFoundException;
import com.matuszak.projects.user.service.UserPersistence;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserPersistence userPersistence;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final ModelMapper modelMapper;

    @GetMapping("/details")
    public ResponseEntity<?> getUserDetails(Principal principal) {

        Optional<User> userByUsername = userPersistence.getUserByUsername(principal.getName());
        userByUsername.ifPresent(user -> modelMapper.map(user, new UserDTO()));
        return new ResponseEntity<>(userByUsername
                        .orElseThrow(() -> new UserNotFoundException("")), HttpStatus.ACCEPTED);
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
}