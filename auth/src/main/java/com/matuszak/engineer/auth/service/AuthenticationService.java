package com.matuszak.engineer.auth.service;


import com.matuszak.engineer.auth.exceptions.EmailAlreadyExistsException;
import com.matuszak.engineer.auth.exceptions.PasswordNotMatchedException;
import com.matuszak.engineer.auth.model.SubjectId;
import com.matuszak.engineer.auth.model.dto.LoginModel;
import com.matuszak.engineer.auth.model.dto.RegisterModel;
import com.matuszak.engineer.auth.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log
public class AuthenticationService {

    private final SubjectRepository subjectRepository;
    private final LoginService loginService;
    private final RegistrationService registrationService;
    private final JwtService jwtService;

    public Map<String, Object> login(LoginModel loginModel) throws LoginException {
        return loginService.login(loginModel);
    }

    public void register(RegisterModel registerModel) throws PasswordNotMatchedException, EmailAlreadyExistsException {
        registrationService.register(registerModel);
    }

    public Boolean isUserExists(SubjectId subjectId){
        return subjectRepository.getSubjectBySubjectId(subjectId).isPresent();
    }

    public void checkAuthentication(String token) {
        jwtService.checkAuthenticationToken(token);
    }
}
