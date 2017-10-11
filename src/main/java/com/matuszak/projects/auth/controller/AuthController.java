package com.matuszak.projects.auth.controller;

import com.matuszak.projects.auth.service.AuthenticationService;
import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.user.service.UserPersistence;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

@Deprecated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Log
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserPersistence userPersistence;

    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody User user) throws IOException {
        log.info("Username: " + user.getUsername());
        return new ResponseEntity<>(authenticationService.authenticate(user), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody User user){
        userPersistence.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}