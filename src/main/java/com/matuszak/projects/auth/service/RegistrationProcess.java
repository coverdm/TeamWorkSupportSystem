package com.matuszak.projects.auth.service;

import com.matuszak.projects.auth.domain.RegisterModel;
import com.matuszak.projects.auth.util.SecurityRole;
import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.auth.exceptions.EmailAlreadyExistsException;
import com.matuszak.projects.auth.exceptions.PasswordNotMatchedException;
import com.matuszak.projects.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log
public class RegistrationProcess {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterModel registerModel) throws PasswordNotMatchedException, EmailAlreadyExistsException{

        log.info("Performing registration...");

        if(!registerModel.getPassword().equals(registerModel.getPassword())){
            throw new PasswordNotMatchedException("Passwords are not matched");
        }

        if(userService.getUserByEmail(registerModel.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("Email already exists");
        }

        log.info("Validation process finished successfully");

        User user = User.builder()
                .email(registerModel.getEmail())
                .password(passwordEncoder.encode(registerModel.getPassword()))
                .username(registerModel.getUsername())
                .securityRole(SecurityRole.USER)
                .build();

        log.info("Saving user...");

        return userService.saveUser(user);
    }
}
