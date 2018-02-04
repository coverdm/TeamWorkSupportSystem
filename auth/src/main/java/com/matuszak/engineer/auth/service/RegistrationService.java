package com.matuszak.engineer.auth.service;

import com.matuszak.engineer.auth.exceptions.EmailAlreadyExistsException;
import com.matuszak.engineer.auth.exceptions.PasswordNotMatchedException;
import com.matuszak.engineer.auth.model.SubjectId;
import com.matuszak.engineer.auth.model.dto.RegisterModel;
import com.matuszak.engineer.auth.model.entity.Subject;
import com.matuszak.engineer.auth.repository.SubjectRepository;
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

    public void register(RegisterModel registerModel) throws PasswordNotMatchedException, EmailAlreadyExistsException {

        log.info("Performing registration...");

        if(!registerModel.getPassword().equals(registerModel.getConfirmPassword()))
            throw new PasswordNotMatchedException("Passwords are not matched");

        if(subjectRepository.getSubjectBySubjectId(new SubjectId(registerModel.getEmail())).isPresent())
            throw new EmailAlreadyExistsException("Email already exists");

        log.info("Validation process finished successfully");

        Subject subject = Subject.builder()
                .subjectId(new SubjectId(registerModel.getEmail()))
                .password(passwordEncoder.encode(registerModel.getPassword()))
                .enabled(Boolean.TRUE)
                .build();

        log.info("Saving user...");

        subjectRepository.save(subject);
    }
}
