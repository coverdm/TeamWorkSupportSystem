package com.matuszak.engineer.domain.auth.service;


import com.matuszak.engineer.domain.auth.exceptions.EmailAlreadyExistsException;
import com.matuszak.engineer.domain.auth.exceptions.PasswordNotMatchedException;
import com.matuszak.engineer.domain.auth.model.dto.LoginModel;
import com.matuszak.engineer.domain.auth.model.dto.RegisterModel;
import com.matuszak.engineer.domain.auth.model.entity.Token;
import com.matuszak.engineer.domain.auth.repository.SubjectRepository;
import com.matuszak.engineer.infrastructure.entity.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log
public class AuthenticationService {

    private final SubjectRepository subjectRepository;
    private final LoginService loginService;
    private final RegistrationService registrationService;

    public Map<String, Object> login(final LoginModel loginModel) throws LoginException {
        return loginService.login(loginModel);
    }

    public void register(final RegisterModel registerModel) throws PasswordNotMatchedException, EmailAlreadyExistsException {
        registrationService.register(registerModel);
    }

    public Boolean isUserExists(UserId userId){
        return subjectRepository.getSubjectByUserId(userId).isPresent();
    }

}
