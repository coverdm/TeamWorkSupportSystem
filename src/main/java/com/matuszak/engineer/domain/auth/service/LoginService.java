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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

@RequiredArgsConstructor
@Service
@Log
public class LoginService {

    private final SubjectRepository subjectRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JwtRepository jwtRepository;

    public Map<String, Object> login(LoginModel loginModel) throws LoginException {

        Map<String, Object> auth = new HashMap<>();

        log.info("Authentication process...");

        Optional<Subject> subjectByEmail = this.subjectRepository.getSubjectByEmail(loginModel.getEmail());

        Subject subject = subjectByEmail
                .filter(e -> isPasswordMatches(loginModel, e))
                .filter(Subject::getEnabled)
                .orElseThrow(() -> new LoginException("Subject is disabled or password doesnt match"));

        log.info("Saving token...");
        Token save = jwtRepository.save(jwtService.createToken(subject));
        log.info("Token saved");

        auth.put("userId", subject.getSubjectId());
        auth.put("token", save.getValue());

        log.info("Auth context: " + auth.toString());

        return auth;
    }

    private boolean isPasswordMatches(LoginModel loginModel, Subject e) {

        if(passwordEncoder.matches(loginModel.getPassword(), e.getPassword()))
            return true;

        log.log(Level.WARNING, "Passwords are not matched");

        return false;
    }
}