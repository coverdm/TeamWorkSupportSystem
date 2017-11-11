package com.matuszak.engineer.boundary.web;

import com.matuszak.engineer.domain.auth.model.dto.LoginModel;

import com.matuszak.engineer.domain.auth.exceptions.EmailAlreadyExistsException;
import com.matuszak.engineer.domain.auth.exceptions.PasswordNotMatchedException;
import com.matuszak.engineer.domain.auth.model.dto.RegisterModel;
import com.matuszak.engineer.domain.auth.model.entity.Token;
import com.matuszak.engineer.domain.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Log
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody @Valid LoginModel loginModel){

        try {
            Token token = authenticationService.login(loginModel);
            return new ResponseEntity<>(token, HttpStatus.OK);
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
}
