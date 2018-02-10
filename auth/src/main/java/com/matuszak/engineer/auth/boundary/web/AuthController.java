package com.matuszak.engineer.auth.boundary.web;

import com.matuszak.engineer.auth.exceptions.*;
import com.matuszak.engineer.auth.model.SubjectId;
import com.matuszak.engineer.auth.model.dto.LoginModel;
import com.matuszak.engineer.auth.model.dto.RegisterModel;
import com.matuszak.engineer.auth.model.entity.Token;
import com.matuszak.engineer.auth.service.AuthFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Log
public class AuthController {

    private final AuthFacade authFacade;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid LoginModel loginModel) {

        try {
            Map<String, Object> auth = authFacade.login(loginModel);
            return new ResponseEntity<>(auth, HttpStatus.OK);
        } catch (LoginException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterModel registerModel) {

        try {
            authFacade.register(registerModel);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PasswordNotMatchedException | EmailAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> isUserExists(@RequestParam String subjectId) {
        Boolean userExists = authFacade.isUserExists(new SubjectId(subjectId));
        return new ResponseEntity<>(userExists, HttpStatus.OK);
    }

    @GetMapping("/{accessToken}/checkAuthorization")
    public ResponseEntity<Boolean> checkAuthentication(@PathVariable("accessToken") String accessToken) {

        try {
            authFacade.checkAuthentication(new Token(accessToken));
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        } catch (NullPointerException | TokenHadBeenExpiredException | IllegalJwtFormatException | UnknownTokenException e) {
            e.printStackTrace();
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/{accessToken}/getAuthenticatedUser")
    public String getAuthenticatedUser(@PathVariable("accessToken") String accessToken) {
        log.info("GET AUTHENTICATED USER " + accessToken);

        String authenticatedUser = authFacade.getAuthenticatedUser(accessToken);

        log.info(authenticatedUser);

        return authenticatedUser;
    }
}