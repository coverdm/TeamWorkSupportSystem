package com.matuszak.engineer.boundary.web;

import com.matuszak.engineer.domain.auth.exceptions.EmailAlreadyExistsException;
import com.matuszak.engineer.domain.auth.exceptions.PasswordNotMatchedException;
import com.matuszak.engineer.domain.auth.model.dto.LoginModel;
import com.matuszak.engineer.domain.auth.model.dto.RegisterModel;
import com.matuszak.engineer.domain.auth.service.AuthenticationService;
import com.matuszak.engineer.infrastructure.entity.UserId;
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
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid LoginModel loginModel){

        try {
            Map<String, Object> auth = authenticationService.login(loginModel);
            return new ResponseEntity<>(auth, HttpStatus.OK);
        } catch (LoginException e) {
            log.info("LoginException invoked");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterModel registerModel){

        try{
            authenticationService.register(registerModel);
            return new ResponseEntity(HttpStatus.OK);
        }catch (PasswordNotMatchedException | EmailAlreadyExistsException e){
            log.info("Register's exceptions invoked");
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> isUserExists(@RequestParam UserId userId){
        Boolean userExists = authenticationService.isUserExists(userId);
        return new ResponseEntity<>(userExists, HttpStatus.OK);
    }
}
