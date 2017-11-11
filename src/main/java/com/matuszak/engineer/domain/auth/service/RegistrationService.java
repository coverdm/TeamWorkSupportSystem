package com.matuszak.engineer.domain.auth.service;

import com.matuszak.engineer.domain.auth.exceptions.EmailAlreadyExistsException;
import com.matuszak.engineer.domain.auth.exceptions.PasswordNotMatchedException;
import com.matuszak.engineer.domain.auth.model.SecurityLevel;
import com.matuszak.engineer.domain.auth.model.dto.RegisterModel;
import com.matuszak.engineer.domain.auth.model.entity.Subject;
import com.matuszak.engineer.domain.auth.repository.SubjectRepository;
import com.matuszak.engineer.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final SubjectRepository subjectRepository;

    public void register(RegisterModel registerModel) throws PasswordNotMatchedException, EmailAlreadyExistsException{

        log.info("Performing registration...");

        if(!registerModel.getPassword().equals(registerModel.getRePassword()))
            throw new PasswordNotMatchedException("Passwords are not matched");

        if(subjectRepository.getSubjectByEmail((registerModel.getEmail())).isPresent())
            throw new EmailAlreadyExistsException("Email already exists");

        log.info("Validation process finished successfully");

        Subject subject = Subject.builder()
                .email(registerModel.getEmail())
                .password(passwordEncoder.encode(registerModel.getPassword()))
                .username(registerModel.getUsername())
                .securityLevel(SecurityLevel.USER)
                .build();

        log.info("Saving user...");

        subjectRepository.save(subject);
    }
}
