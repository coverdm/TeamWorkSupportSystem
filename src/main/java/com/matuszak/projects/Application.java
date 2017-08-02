package com.matuszak.projects;

import com.matuszak.projects.authorization.Role;
import com.matuszak.projects.user.User;
import com.matuszak.projects.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan
public class Application{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Application(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void setUpDatabase(){

        User build = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .enabled(true)
                .email("admin@admin.com")
                .userRole(Role.ADMIN)
                .firstName("dawid")
                .lastName("matuszak")
                .build();

        User user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .enabled(true)
                .email("userowo@mobile.com")
                .userRole(Role.USER)
                .firstName("kasia")
                .lastName("nowak")
                .build();

        this.userRepository.save(Arrays.asList(user,build));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}