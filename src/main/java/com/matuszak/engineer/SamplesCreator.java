package com.matuszak.engineer;

import com.matuszak.engineer.domain.auth.model.SecurityLevel;
import com.matuszak.engineer.domain.auth.model.entity.Subject;
import com.matuszak.engineer.domain.auth.model.entity.SubjectId;
import com.matuszak.engineer.domain.auth.repository.SubjectRepository;
import com.matuszak.engineer.domain.project.model.ParticipantId;
import com.matuszak.engineer.domain.project.model.ParticipantLevel;
import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.ProjectProperties;
import com.matuszak.engineer.domain.project.model.entity.Participant;
import com.matuszak.engineer.domain.project.model.entity.Project;
import com.matuszak.engineer.domain.project.repository.ParticipantRepository;
import com.matuszak.engineer.domain.project.repository.ProjectRepository;
import com.matuszak.engineer.infrastructure.entity.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log
public class SamplesCreator {

    private final ProjectRepository projectRepository;
    private final ParticipantRepository participantRepository;
    private final SubjectRepository subjectRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void setUpDatabase(){

        final String password = "JakisPassword";
        final String email = "admin@admin.com";
        final String username = "username";

        UserId id = new UserId(1l);

        Subject subject = new Subject(new SubjectId(id), email,username, passwordEncoder.encode(password),
                SecurityLevel.ADMIN, true, null);

        Participant programmer =
                new Participant(new ParticipantId(id), ParticipantLevel.PROGRAMMER);

        Participant graphicsDesigner =
                new Participant(new ParticipantId(id), ParticipantLevel.GRAPHICS_DESIGNER);

        Project project = new Project(
                new ProjectId(UUID.randomUUID().toString()), new ProjectProperties("jakisProjekt", "jakisOpis")
        );

        this.subjectRepository.save(subject);
        this.participantRepository.save(Arrays.asList(graphicsDesigner, programmer));
        this.projectRepository.save(project);
    }
}