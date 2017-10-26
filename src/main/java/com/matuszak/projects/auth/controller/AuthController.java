package com.matuszak.projects.auth.controller;

import com.matuszak.projects.auth.domain.LoginModel;
import com.matuszak.projects.auth.domain.RegisterModel;
import com.matuszak.projects.auth.domain.Token;
import com.matuszak.projects.auth.service.AuthenticationService;
import com.matuszak.projects.auth.service.LoginService;
import com.matuszak.projects.auth.service.RegistrationService;
import com.matuszak.projects.auth.exceptions.EmailAlreadyExistsException;
import com.matuszak.projects.auth.exceptions.PasswordNotMatchedException;
import com.matuszak.projects.user.dto.UserDTO;
import com.matuszak.projects.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Log
@RequestMapping("/api/auth")
public class AuthController {

    private final ModelMapper modelMapper;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody @Valid LoginModel loginModel){

        try {
            Token token = authenticationService.login(loginModel);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterModel registerModel){

        try{

            User register = authenticationService.register(registerModel);
            UserDTO userDTO = modelMapper.map(register, UserDTO.class);

            return new ResponseEntity(userDTO, HttpStatus.OK);
        }catch (PasswordNotMatchedException | EmailAlreadyExistsException  e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
