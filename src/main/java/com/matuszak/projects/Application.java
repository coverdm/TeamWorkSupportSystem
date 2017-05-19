package com.matuszak.projects;

import com.matuszak.projects.domain.User;
import com.matuszak.projects.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
            Stream.of("admin,admin,true,ROLE_ADMIN", "user,user,false,USER", "kasia,angelus,true,Admin")
                    .map( tpl -> tpl.split(","))
                    .forEach(tpl->{

                        List<String> userRoles = new ArrayList<>();
                        userRoles.add(tpl[3]);

                        userRepository.save(new User(tpl[0], tpl[1], Boolean.parseBoolean(tpl[2]), null, userRoles));
                    });
        };
    }


}