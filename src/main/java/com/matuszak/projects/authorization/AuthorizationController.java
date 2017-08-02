package com.matuszak.projects.authorization;

import com.matuszak.projects.user.User;
import com.matuszak.projects.user.UserPersistence;
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
public class AuthorizationController {

    private final AuthenticationService authenticationService;
    private final UserPersistence userPersistence;
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public AuthorizationController(AuthenticationService authenticationService,UserPersistence userPersistence) {
        this.authenticationService = authenticationService;
        this.userPersistence = userPersistence;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody User user, HttpServletResponse response) throws IOException {
        logger.info("Username: " + user.getUsername());
        return new ResponseEntity<>(authenticationService.authenticate(user), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody User user){
        userPersistence.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}