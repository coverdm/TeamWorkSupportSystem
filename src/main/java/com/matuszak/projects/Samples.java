package com.matuszak.projects;

import com.matuszak.projects.auth.util.SecurityRole;
import com.matuszak.projects.project.entity.Project;
import com.matuszak.projects.project.entity.ProjectStatus;
import com.matuszak.projects.project.repository.ProjectRepository;
import com.matuszak.projects.task.entity.Task;
import com.matuszak.projects.task.repository.TaskRepository;
import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.user.repository.UserRepository;
import com.matuszak.projects.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log
public class Samples {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @PostConstruct
    public void setUpDatabase(){

        User user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .enabled(true)
                .email("userowo@mobile.com")
                .securityRole(SecurityRole.USER)
                .build();

        User participant = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .enabled(true)
                .email("admin@admin.com")
                .securityRole(SecurityRole.ADMIN)
                .build();

        User disabledUser = User.builder()
                .username("diss")
                .password(passwordEncoder.encode("disableed"))
                .enabled(false)
                .email("emailed@emial.com")
                .securityRole(SecurityRole.USER)
                .build();

        Project project = Project.builder()
                .name("someProject")
                .uuid(UUID.randomUUID().toString())
                .owner(user)
                .participants(Arrays.asList(participant))
                .description("someDescription")
                .createdDate(LocalDate.now())
                .tasks(new ArrayList<>())
                .status(ProjectStatus.FINISHED)
                .build();

        Task task = Task.builder()
                .employee(user)
                .description("Add new aspects")
                .deadline(LocalDate.now())
                .build();

        project.getTasks().add(task);

        this.userRepository.save(Arrays.asList(user,participant,disabledUser));
        this.taskRepository.save(task);
        this.projectRepository.save(project);
    }
}
