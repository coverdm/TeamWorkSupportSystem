package com.matuszak.projects.auth.controller;

import com.matuszak.projects.auth.service.AuthService;
import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.user.service.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserPersistence userPersistence;
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public AuthController(AuthService authService, UserPersistence userPersistence) {
        this.authService = authService;
        this.userPersistence = userPersistence;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody User user, HttpServletResponse response) throws IOException {
        logger.info("Username: " + user.getUsername());
        return new ResponseEntity<>(authService.authenticate(user), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody User user){
        userPersistence.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}