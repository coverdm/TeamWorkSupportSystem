package com.matuszak.projects;

import com.matuszak.projects.auth.util.Role;
import com.matuszak.projects.project.entity.Project;
import com.matuszak.projects.project.repository.ProjectRepository;
import com.matuszak.projects.project.entity.ProjectStatus;
import com.matuszak.projects.task.entity.Task;
import com.matuszak.projects.task.repository.TaskRepository;
import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.user.repository.UserRepository;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan
@Log
public class Application{

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final PasswordEncoder passwordEncoder;
    private final TaskRepository taskRepository;
    private final ApplicationContext applicationContext;

    @Autowired
    public Application(UserRepository userRepository,
                       ProjectRepository projectRepository,
                       PasswordEncoder passwordEncoder,
                       TaskRepository taskRepository,
                       ApplicationContext applicationContext) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.passwordEncoder = passwordEncoder;
        this.taskRepository = taskRepository;
        this.applicationContext = applicationContext;
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @PostConstruct
    public void setUpDatabase(){

        User user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .enabled(true)
                .email("userowo@mobile.com")
                .userRole(Role.USER)
                .firstName("adam")
                .lastName("nowak")
                .build();

        User participant = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .enabled(true)
                .email("admin@admin.com")
                .userRole(Role.ADMIN)
                .firstName("dawid")
                .lastName("replay")
                .build();

        User disabledUser = User.builder()
                .username("diss")
                .password(passwordEncoder.encode("disableed"))
                .enabled(false)
                .email("emailed@emial.com")
                .userRole(Role.USER)
                .firstName("klaudia")
                .lastName("janosik")
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

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}