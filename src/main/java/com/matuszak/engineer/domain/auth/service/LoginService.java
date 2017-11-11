package com.matuszak.engineer.domain.auth.service;

import com.matuszak.engineer.domain.auth.model.dto.LoginModel;
import com.matuszak.engineer.domain.auth.model.entity.Subject;
import com.matuszak.engineer.domain.auth.model.entity.Token;
import com.matuszak.engineer.domain.auth.repository.JwtRepository;
import com.matuszak.engineer.domain.auth.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.logging.Level;

@RequiredArgsConstructor
@Service
@Log
public class LoginService {

    private final SubjectRepository subjectRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JwtRepository jwtRepository;

    public Token login(LoginModel loginModel) throws LoginException {

        log.info("Authentication process...");

        Subject subject = this.subjectRepository.getSubjectByEmail(loginModel.getEmail())
                .filter(e -> isPasswordMatches(loginModel, e))
                .filter(Subject::getEnabled)
                .orElseThrow(LoginException::new);


        return jwtRepository.save(jwtService.createToken(subject));
    }

    private boolean isPasswordMatches(LoginModel loginModel, Subject e) {

        if(passwordEncoder.matches(loginModel.getPassword(), e.getPassword()))
            return true;

        log.log(Level.WARNING, "Passwords are not matched");

        return false;
    }
}