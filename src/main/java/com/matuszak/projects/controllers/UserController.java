package com.matuszak.projects.controllers;

import com.matuszak.projects.domain.UserDetails;
import com.matuszak.projects.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * Created by dawid on 18.06.17.
 */

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/rest/user")
@RestController
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/details")
    public ResponseEntity<UserDetails> getUserDetails(Principal principal, HttpServletResponse httpServletResponse){
        UserDetails userDetails = userRepository.findUserByUsername(principal.getName()).getUserDetails();
        return new ResponseEntity<UserDetails>(userDetails, HttpStatus.ACCEPTED);
    }
}
