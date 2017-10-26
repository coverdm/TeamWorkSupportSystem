package com.matuszak.projects.auth.service;

import com.matuszak.projects.auth.domain.LoginModel;
import com.matuszak.projects.auth.domain.RegisterModel;
import com.matuszak.projects.auth.domain.Token;
import com.matuszak.projects.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
@RequiredArgsConstructor
@Log
public class AuthenticationService {

    private final LoginService loginService;
    private final RegistrationService registrationService;

    public Token login(final LoginModel loginModel) throws AuthenticationException {
        return loginService.login(loginModel);
    }

    public User register(final RegisterModel registerModel){
        return registrationService.register(registerModel);
    }

}
