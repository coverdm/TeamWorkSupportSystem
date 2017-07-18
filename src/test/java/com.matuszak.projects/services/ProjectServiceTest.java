package com.matuszak.projects.services;

import com.matuszak.projects.project.Project;
import com.matuszak.projects.project.ProjectService;
import com.matuszak.projects.user.User;
import com.matuszak.projects.util.ProgrammingLanguages;
import com.matuszak.projects.authorization.Role;
import com.matuszak.projects.project.ProjectRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by dawid on 26.06.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {

    @MockBean
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @Test
    public void shouldReturnOnlyOwnedProjects(){

        User user1 = User.builder()
                .username("username")
                .password("password")
                .userRole(Role.ADMIN)
                .email("email@gmail.com")
                .experience(Arrays.asList(ProgrammingLanguages.JAVA, ProgrammingLanguages.PHP))
                .enabled(true)
                .firstName("firstName")
                .lastName("lastName")
                .build();

        Project project = Project.builder()
                .name("someProject")
                .owner(user1)
                .participants(null)
                .technologies(null)
                .description("someDescription")
                .createdDate(LocalDate.now())
                .build();

        Mockito.when(projectRepository.getProjectsByOwnerUsername(user1.getUsername())).thenReturn(Arrays.asList(project));

        Assert.assertEquals(Arrays.asList(project), this.projectService.getProjectsByOwnerUsername(user1.getUsername()));
    }

    @Test
    public void shouldAddNewParticipants(){

        User user1 = User.builder()
                .username("someUsername")
                .password("somePassword")
                .userRole(Role.USER)
                .email("someEmail@gmail.com")
                .experience(Arrays.asList(ProgrammingLanguages.JAVA, ProgrammingLanguages.PHP))
                .enabled(true)
                .firstName("someFirstName")
                .lastName("someLastName")
                .build();

        User user2 = User.builder()
                .username("2Username")
                .password("2Password")
                .userRole(Role.USER)
                .email("s2mail@gmail.com")
                .experience(Arrays.asList(ProgrammingLanguages.JAVA, ProgrammingLanguages.PHP))
                .enabled(true)
                .firstName("someF2stName")
                .lastName("som2tName")
                .build();

        String uuid = UUID.randomUUID().toString();

        Project project = Project.builder()
                .name("someProject")
                .uuid(uuid)
                .owner(user1)
                .participants(new ArrayList<>())
                .technologies(null)
                .description("someDescription")
                .build();

        Mockito.when(this.projectRepository.getProjectByUuid(uuid)).thenReturn(Optional.of(project));

        project.getParticipants().addAll(Arrays.asList(user1,user2));

        Assert.assertEquals(project, this.projectService.addParticipants(uuid, Arrays.asList(user1, user2)));

    }
}