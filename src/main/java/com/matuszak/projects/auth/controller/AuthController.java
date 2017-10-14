package com.matuszak.projects.auth.controller;

import com.matuszak.projects.auth.domain.LoginModel;
import com.matuszak.projects.auth.domain.RegisterModel;
import com.matuszak.projects.auth.service.AuthenticationService;
import com.matuszak.projects.auth.service.RegistrationProcess;
import com.matuszak.projects.auth.exceptions.EmailAlreadyExistsException;
import com.matuszak.projects.auth.exceptions.PasswordNotMatchedException;
import com.matuszak.projects.user.dto.UserDTO;
import com.matuszak.projects.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Log
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final RegistrationProcess registrationProcess;
    private final ModelMapper modelMapper;

    @RequestMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody LoginModel loginModel){

        try {

            Map<String, Object> authenticate = authenticationService.authenticate(loginModel);
            return new ResponseEntity<>(authenticate, HttpStatus.OK);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterModel registerModel){

        try{
            User register = registrationProcess.register(registerModel);

            UserDTO userDTO = modelMapper.map(register, UserDTO.class);

            return new ResponseEntity(userDTO, HttpStatus.OK);
        }catch (PasswordNotMatchedException | EmailAlreadyExistsException  e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
