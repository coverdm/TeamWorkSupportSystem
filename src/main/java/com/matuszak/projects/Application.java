package com.matuszak.projects;

import com.matuszak.projects.auth.util.SecurityRole;
import com.matuszak.projects.project.entity.Project;
import com.matuszak.projects.project.repository.ProjectRepository;
import com.matuszak.projects.project.entity.ProjectStatus;
import com.matuszak.projects.task.entity.Task;
import com.matuszak.projects.task.repository.TaskRepository;
import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@RequiredArgsConstructor
public class Application{

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}