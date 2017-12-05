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
import java.util.Arrays;

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

        final String password = "JakisPassword123";
        final String email = "admin@admin.com";
        final String username = "username";

        Subject subject = new Subject(new SubjectId(), email,username, passwordEncoder.encode(password),
                SecurityLevel.ADMIN, Boolean.TRUE, null);

        String secEmail = "user@user.com";
        String secUsername = "secUsername";
        Subject secondSubject = new Subject(new SubjectId(), secEmail, secUsername, passwordEncoder.encode(password), SecurityLevel.USER, Boolean.TRUE, null);

        this.subjectRepository.save(Arrays.asList(secondSubject, subject));
    }
}