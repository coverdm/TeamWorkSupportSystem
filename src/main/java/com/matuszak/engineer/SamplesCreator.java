package com.matuszak.engineer;

import com.matuszak.engineer.domain.auth.model.SecurityLevel;
import com.matuszak.engineer.domain.auth.model.SubjectId;
import com.matuszak.engineer.domain.auth.model.entity.Subject;
import com.matuszak.engineer.domain.auth.repository.SubjectRepository;
import com.matuszak.engineer.domain.auth.service.RegistrationService;
import com.matuszak.engineer.domain.project.model.ProjectRole;
import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.ProjectProperties;
import com.matuszak.engineer.domain.project.model.entity.Worker;
import com.matuszak.engineer.domain.project.model.entity.Project;
import com.matuszak.engineer.domain.project.repository.WorkerRepository;
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
    private final WorkerRepository workerRepository;
    private final SubjectRepository subjectRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationService registrationService;

    @PostConstruct
    public void setUpDatabase(){

        final String password = "JakisPassword";
        final String email = "dawid_matuszak@outlook.com";
        final String email2 = "mateusz_stanek@gmail.com";
        final String email3 = "marcin_tatus@onet.pl";
        final String email4 = "michal_golabek@kurnik.pl";

        final String username = "username";

        Subject subject = new Subject(new SubjectId(email),username, passwordEncoder.encode(password),
                Boolean.TRUE, SecurityLevel.ADMIN, null);

        Subject subject2 = new Subject(new SubjectId(email2),username, passwordEncoder.encode(password),
                Boolean.TRUE, SecurityLevel.USER, null);

        Subject subject3 = new Subject(new SubjectId(email3),username, passwordEncoder.encode(password),
                Boolean.TRUE, SecurityLevel.USER, null);

        Subject subject4 = new Subject(new SubjectId(email4),username, passwordEncoder.encode(password),
                Boolean.TRUE, SecurityLevel.USER, null);

        this.subjectRepository.save(Arrays.asList(subject, subject2, subject3, subject4));

        Project firstProject = new Project(new ProjectId(UUID.randomUUID().toString()), new ProjectProperties("first project", "here is gonna be some description"));
        Project secProject = new Project(new ProjectId(UUID.randomUUID().toString()), new ProjectProperties("first project", "here is gonna be some description"));

        Worker worker = new Worker(new UserId(email), ProjectRole.OWNER);
        Worker worker2 = new Worker(new UserId(email), ProjectRole.OWNER);

        Worker worker3 = new Worker(new UserId(email2), ProjectRole.SENIOR_JAVA_DEVELOPER);
        Worker worker4 = new Worker(new UserId(email2), ProjectRole.SENIOR_JAVA_DEVELOPER);
        Worker worker5 = new Worker(new UserId(email3), ProjectRole.SENIOR_JAVA_DEVELOPER);
        Worker worker6 = new Worker(new UserId(email4), ProjectRole.SENIOR_JAVA_DEVELOPER);
        Worker worker7 = new Worker(new UserId(email4), ProjectRole.GRAPHICS_DESIGNER);

        this.workerRepository.save(Arrays.asList(worker, worker2, worker3,
                worker4, worker5, worker6, worker7));

        firstProject.addWorker(worker);
        firstProject.addWorker(worker3);
        firstProject.addWorker(worker5);
        firstProject.addWorker(worker7);

        secProject.addWorker(worker2);
        secProject.addWorker(worker6);
        secProject.addWorker(worker4);

        this.projectRepository.save(Arrays.asList(firstProject));
        this.projectRepository.save(Arrays.asList(secProject));
    }
}