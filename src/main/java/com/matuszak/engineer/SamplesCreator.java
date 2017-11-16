package com.matuszak.engineer;

import com.matuszak.engineer.domain.auth.model.SecurityLevel;
import com.matuszak.engineer.domain.auth.model.entity.Subject;
import com.matuszak.engineer.domain.auth.model.entity.SubjectId;
import com.matuszak.engineer.domain.auth.repository.SubjectRepository;
import com.matuszak.engineer.domain.auth.service.RegistrationService;
import com.matuszak.engineer.domain.project.repository.ParticipantRepository;
import com.matuszak.engineer.domain.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Log
public class SamplesCreator {

    private final ProjectRepository projectRepository;
    private final ParticipantRepository participantRepository;
    private final SubjectRepository subjectRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationService registrationService;

    @PostConstruct
    public void setUpDatabase(){

        final String password = "JakisPassword";
        final String email = "admin@admin.com";
        final String username = "username";

        Subject subject = new Subject(new SubjectId(), email,username, passwordEncoder.encode(password),
                SecurityLevel.ADMIN, Boolean.TRUE, null);

        this.subjectRepository.save(subject);
    }
}