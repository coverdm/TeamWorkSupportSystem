package com.matuszak.projects;

import com.matuszak.projects.project.ProjectRepository;
import com.matuszak.projects.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by dawid on 16.02.17.
 */

@SpringBootApplication
@EnableWebMvc
public class Application {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @PostConstruct
//    public void setUpDatabase(){
//
//        User admin = User.builder()
//                .username("admin")
//                .password("admin")
//                .email("admin@admin.com")
//                .enabled(true)
//                .userRole(Role.USER)
//                .experience(Arrays.asList(ProgrammingLanguages.JAVA, ProgrammingLanguages.PHP))
//                .firstName("Admin")
//                .lastName("Admin")
//                .build();
//
//        User user = User.builder()
//                .username("user")
//                .password("user")
//                .email("user@gmail.com")
//                .enabled(true)
//                .userRole(Role.USER)
//                .experience(Arrays.asList(ProgrammingLanguages.JAVA, ProgrammingLanguages.PHP))
//                .firstName("Mateusz")
//                .lastName("Stanek")
//                .build();
//
//        Project project = Project.builder()
//                .createdDate(new Date(new GregorianCalendar().getTime().getTime()))
//                .name("FirstProject")
//                .uuid("12312-23123-421123-213123")
//                .description("FirstDescription")
//                .owner(user)
//                .participants(Arrays.asList(user))
//                .technologies(Arrays.asList(ProgrammingLanguages.PHP))
//                .build();
//
//        this.userRepository.save(Arrays.asList(user,admin));
//        this.projectRepository.save(project);
//    }
}