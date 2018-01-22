package com.matuszak.engineer;

import com.matuszak.engineer.domain.auth.model.SubjectId;
import com.matuszak.engineer.domain.auth.model.entity.Subject;
import com.matuszak.engineer.domain.auth.repository.SubjectRepository;
import com.matuszak.engineer.domain.profile.model.*;
import com.matuszak.engineer.domain.profile.model.entity.Profile;
import com.matuszak.engineer.domain.profile.repository.ProfileRepository;
import com.matuszak.engineer.domain.project.model.*;
import com.matuszak.engineer.domain.project.model.entity.Project;
import com.matuszak.engineer.domain.project.model.entity.Task;
import com.matuszak.engineer.domain.project.repository.ProjectRepository;
import com.matuszak.engineer.domain.project.repository.TaskRepository;
import com.matuszak.engineer.domain.project.repository.WorkerRepository;
import com.matuszak.engineer.domain.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log
public class SamplesCreator implements ApplicationListener<ContextRefreshedEvent> {

    private final ProjectRepository projectRepository;
    private final WorkerRepository workerRepository;
    private final SubjectRepository subjectRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;
    private final TaskRepository taskRepository;
    private final ProjectService projectService;

    Subject dawidMatuszak, mateuszStanek, marcinTatus, michalGolabek, dawidSikorski;
    Profile dawidMatuszakProfile, mateuszStanekProfile, marcinTatusProfile, michalGolabekProfile, dawidSikorskiProfile;
    Project epomis, rumble;
    Task ratpackBadanie, springImplementation, authModuleImplementation, jpaBadanie;


    private void setUpSubjects(){

        final String password = "Mamcie12";

        this.dawidMatuszak = new Subject(new SubjectId("dawid_matuszak@outlook.com"), passwordEncoder.encode(password),
                Boolean.TRUE, null);

        this.mateuszStanek = new Subject(new SubjectId("mateusz_stanek@gmail.com"), passwordEncoder.encode(password),
                Boolean.TRUE, null);

        this.marcinTatus = new Subject(new SubjectId("marcin_tatus@onet.pl"), passwordEncoder.encode(password),
                Boolean.TRUE, null);

        this.michalGolabek = new Subject(new SubjectId("michal_golabek@kurnik.pl"), passwordEncoder.encode(password),
                Boolean.TRUE, null);

        this.dawidSikorski = new Subject(new SubjectId("dawid_sikorski@live.pl"), passwordEncoder.encode(password),
                Boolean.TRUE, null);

        this.subjectRepository.save(Arrays.asList(this.dawidMatuszak, this.mateuszStanek, this.marcinTatus, this.michalGolabek, this.dawidSikorski));

        log.info("Subjects registered");
    }

    private void setUpProfiles(){


        this.dawidMatuszakProfile = Profile.builder()
                .name(new Name("Dawid", "Matuszak"))
                .prefferedRoles(Arrays.asList(new PrefferedRole("Junior Java Developer"), new PrefferedRole("Junior Backend developer")))
                .avatar(null)
                .contact(new Contact("jakisTamSkypeProfile", "123423123"))
                .profileId(new ProfileId(dawidMatuszak.getSubjectId()))
                .skills(Arrays.asList(new Skill("Java"), new Skill("Angular 4"), new Skill("Hibernate"), new Skill("JPA"), new Skill("Spring"), new Skill("Ratpack")))
                .build();

        this.mateuszStanekProfile = Profile.builder()
                .name(new Name("Mateusz", "Stanek"))
                .prefferedRoles(Arrays.asList(new PrefferedRole("Junior Javascript Developer"), new PrefferedRole("Junior C# Developer")))
                .avatar(null)
                .contact(new Contact("mateuszStankeklive", "321452312"))
                .profileId(new ProfileId(mateuszStanek.getSubjectId()))
                .skills(Arrays.asList(new Skill("C#"), new Skill("Angular 4"), new Skill("NodeJS"), new Skill("Javascript"), new Skill("React"), new Skill(".NET")))
                .build();

        this.marcinTatusProfile = Profile.builder()
                .name(new Name("Marcin", "Tatus"))
                .prefferedRoles(Arrays.asList(new PrefferedRole("Junior Frontend developer"), new PrefferedRole("Junior engineer developer"), new PrefferedRole("Junior projectManager")))
                .avatar(null)
                .contact(new Contact("skypowoTatusiowo", "872341092"))
                .profileId(new ProfileId(marcinTatus.getSubjectId()))
                .skills(Arrays.asList(new Skill("Javascript"), new Skill("Angular 4"), new Skill("React"), new Skill("Mongo"), new Skill("Agile"), new Skill("Ruby and Rails")))
                .build();

        this.michalGolabekProfile = Profile.builder()
                .name(new Name("Michal", "Golabek"))
                .prefferedRoles(Arrays.asList(new PrefferedRole("Graphics designer"), new PrefferedRole("Junior UI designer")))
                .avatar(null)
                .contact(new Contact("professionalSkype", "981324512"))
                .profileId(new ProfileId(michalGolabek.getSubjectId()))
                .skills(Arrays.asList(new Skill("Photoshop"), new Skill("InDesign"), new Skill("Gimp"), new Skill("Illustrator")))
                .build();

        this.dawidSikorskiProfile = Profile.builder()
                .name(new Name("Dawid", "Sikorski"))
                .prefferedRoles(Arrays.asList(new PrefferedRole("Php developer"), new PrefferedRole("Frontend developer")))
                .avatar(null)
                .contact(new Contact("OnlyPhpManiac", "782432543"))
                .profileId(new ProfileId(dawidSikorski.getSubjectId()))
                .skills(Arrays.asList(new Skill("Java"), new Skill("Php"), new Skill("Laravel"), new Skill("Html5"), new Skill("CSS3"), new Skill("Javascript")))
                .build();

        this.profileRepository.save(Arrays.asList(this.dawidMatuszakProfile, this.mateuszStanekProfile, this.michalGolabekProfile, this.marcinTatusProfile, this.dawidSikorskiProfile));

        log.info("Profiles assigned");
    }

    private void setUpProject(){
        this.epomis = new Project(new ProjectId(UUID.randomUUID().toString()), new ProjectProperties("epomis", "here is gonna be some description"));
        this.rumble = new Project(new ProjectId(UUID.randomUUID().toString()), new ProjectProperties("rumble", "here is gonna be some description"));

        log.info("Projects objects created");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.setUpSubjects();
        this.setUpProfiles();
    }
}