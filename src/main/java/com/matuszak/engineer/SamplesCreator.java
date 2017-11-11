package com.matuszak.engineer;

import com.matuszak.engineer.domain.auth.model.SecurityLevel;
import com.matuszak.engineer.domain.auth.model.entity.Subject;
import com.matuszak.engineer.domain.auth.repository.SubjectRepository;
import com.matuszak.engineer.domain.project.entity.Participant;
import com.matuszak.engineer.domain.project.entity.Project;
import com.matuszak.engineer.domain.project.model.ParticipantLevel;
import com.matuszak.engineer.domain.project.repository.ParticipantRepository;
import com.matuszak.engineer.domain.project.repository.ProjectRepository;
import com.matuszak.engineer.domain.user.entity.User;
import com.matuszak.engineer.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log
public class SamplesCreator {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ParticipantRepository participantRepository;
    private final SubjectRepository subjectRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void setUpDatabase(){

        User user = new User();
        user.setEmail("admin@admin.com");

        User participant = new User();
        participant.setEmail("jakis@email.com");

        User disabledUser = new User();
        disabledUser.setEmail("disabled@user.pl");

        Participant projectManager = Participant.builder()
                .userID(1l)
                .level(ParticipantLevel.PROJECT_MANAGER)
                .build();

        Participant programmer = Participant.builder()
                .userID(2l)
                .level(ParticipantLevel.PROGRAMMER)
                .build();

        Subject subject = new Subject("admin@admin.com", "jakisusername", passwordEncoder.encode("JakisPassword"),
                SecurityLevel.ADMIN, true, null);
        this.subjectRepository.save(subject);

        this.participantRepository.save(Arrays.asList(projectManager, programmer));

        String uuid = UUID.randomUUID().toString();

        Project project = new Project(uuid, "JakisProjekt");
        project.addParticipant(programmer);

        this.projectRepository.save(project);

        this.userRepository.save(Arrays.asList(user,participant,disabledUser));



    }
}
