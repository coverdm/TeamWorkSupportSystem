package com.matuszak.projects.authorization;

import com.matuszak.projects.user.User;
import com.matuszak.projects.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by dawid on 16.02.17.
 */

@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public AuthorizationController(AuthenticationService authenticationService,UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody User user, HttpServletResponse response) throws IOException {
        logger.info(user.getUsername() + " " + user.getPassword());
        return new ResponseEntity<>(authenticationService.authenticate(user, response), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody User user){
        userService.saveUser(user);
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }
}