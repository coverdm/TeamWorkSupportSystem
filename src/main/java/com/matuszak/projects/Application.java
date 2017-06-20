package com.matuszak.projects;

import com.matuszak.projects.domain.User;
import com.matuszak.projects.domain.UserDetails;
import com.matuszak.projects.repositories.UserDetailsRepository;
import com.matuszak.projects.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by dawid on 16.02.17.
 */

@SpringBootApplication
@EnableWebMvc
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Bean
    CommandLineRunner commandLineRunner(final UserRepository userRepository, final UserDetailsRepository userDetailsRepository){
        return args -> {
            Stream.of("admin,admin,true,ROLE_ADMIN,Ruda Śląska,2 Lata w Euvic,Zadnych,Dawid,Matuszak",
                    "user,user,false,USER,Katowice,Brak,Zadnych,Pawel,Hawryluk",
                    "kasia,angelus,true,Admin,Katowice,3 lata grafik,12 lat szkoly,Kasia,Angelus")
                    .map( tpl -> tpl.split(","))
                    .forEach(tpl->{

                        List<String> userRoles = new ArrayList<>();
                        userRoles.add(tpl[3]);
                        UserDetails userDetails = new UserDetails();
                        userDetails.setCity(tpl[4]);
                        userDetails.setExperience(tpl[5]);
                        userDetails.setFinishedSchools(tpl[6]);
                        userDetails.setFirstName(tpl[7]);
                        userDetails.setLastName(tpl[8]);
                        userDetailsRepository.save(userDetails);

                        userRepository.save(new User(tpl[0], tpl[1], Boolean.parseBoolean(tpl[2]), userDetails, userRoles));

                    });
        };
    }


}