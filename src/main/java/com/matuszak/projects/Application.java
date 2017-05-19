package com.matuszak.projects;

import com.matuszak.projects.domain.Project;
import com.matuszak.projects.domain.User;
import com.matuszak.projects.domain.UserDetails;
import com.matuszak.projects.domain.UserRole;
import com.matuszak.projects.repositories.ProjectRepository;
import com.matuszak.projects.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by dawid on 16.02.17.
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(final UserRepository userRepository){
        return args -> {
            Stream.of("admin,admin,true,Admin", "user,user,false,JakasTamRola", "kasia,angelus,true,Admin")
                    .map( tpl -> tpl.split(","))
                    .forEach(tpl->{
                        userRepository.save(new User(tpl[0], tpl[1], Boolean.parseBoolean(tpl[2]), null, null));
                    });
        };
    }


}