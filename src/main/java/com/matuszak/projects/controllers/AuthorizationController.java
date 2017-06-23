package com.matuszak.projects.controllers;

import com.matuszak.projects.domain.User;
import com.matuszak.projects.repositories.UserRepository;
import com.matuszak.projects.secure.AuthenticationService;
import com.matuszak.projects.services.ProjectService;
import com.matuszak.projects.services.UserService;
import org.hibernate.internal.SessionFactoryRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dawid on 16.02.17.
 */

@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {

    private AuthenticationService authenticationService;
    private UserService userService;
    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public AuthorizationController(final AuthenticationService authenticationService,final UserService userService) {
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
        userService.save(user);
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }
}