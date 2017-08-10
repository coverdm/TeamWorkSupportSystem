package com.matuszak.projects.project;

import com.matuszak.projects.Application;
import com.matuszak.projects.TestConfiguration;
import com.matuszak.projects.auth.util.Role;
import com.matuszak.projects.project.entity.Project;
import com.matuszak.projects.project.entity.ProjectStatus;
import com.matuszak.projects.project.exceptions.ProjectNotFoundException;
import com.matuszak.projects.project.repository.ProjectRepository;
import com.matuszak.projects.project.service.ProjectPersistence;
import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, TestConfiguration.class})
public class ProjectPersistenceTest {

    @MockBean private ProjectRepository projectRepository;
    @MockBean private UserRepository userRepository;

    @Autowired private ProjectPersistence projectPersistence;
    @Autowired private AuthenticationManager authenticationManager;

    private Authentication authentication;
    private User user1, user2;
    private Project project1, project2;

    @Before
    public void init(){

        this.authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        "admin",
                        "admin")
        );


        this.user1 = User.builder()
                .username(this.authentication.getName())
                .password("somePassword")
                .userRole(Role.USER)
                .email("someEmail@gmail.com")
                .enabled(true)
                .firstName("someFirstName")
                .lastName("someLastName")
                .build();

        this.user2 = User.builder()
                .username("2Username")
                .password("2Password")
                .userRole(Role.USER)
                .email("s2mail@gmail.com")
                .enabled(true)
                .firstName("someF2stName")
                .lastName("som2tName")
                .build();

        this.project1 = Project.builder()
                .name("someProject")
                .uuid(UUID.randomUUID().toString())
                .owner(this.user1)
                .participants(new ArrayList<>())
                .description("someDescription")
                .createdDate(LocalDate.now())
                .status(ProjectStatus.FINISHED)
                .build();

        this.project2 = Project.builder()
                .name("secondProject")
                .uuid(UUID.randomUUID().toString())
                .owner(this.user2)
                .participants(new ArrayList<>())
                .createdDate(LocalDate.now())
                .status(ProjectStatus.OPEN)
                .description("Second description for project")
                .build();

    }

    @After
    public void clearSecurityContext(){
        SecurityContextHolder.clearContext();
    }

    @Test
    public void shouldReturnOnlyOwnedProjects(){

        //given
        List<Project> projects = Arrays.asList(this.project1);
        String username = this.user1.getUsername();

        when(projectRepository.getProjectsByOwnerUsername(username)).thenReturn(projects);

        //when
        List<Project> ownedProjects = this.projectPersistence.getProjectsByOwnerUsername(username);

        //then
        Assertions.assertThat(projects).isEqualTo(ownedProjects);
    }

    @Test
    public void shouldReturnListOfProjects(){

        //given
        List<Project> projects = Arrays.asList(this.project1, this.project2);

        when(projectRepository.findAll()).thenReturn(projects);

        //when
        List<Project> gotProjects = this.projectPersistence.getProjects();

        //then
        Assertions.assertThat(projects).isEqualTo(gotProjects);
    }

    @Test
    public void shouldThrowAnExceptionWhenProjectHadBeenNotFound(){

        //given
        String wrongUUID = UUID.randomUUID().toString();
        Optional<Project> project = Optional.ofNullable(null);

        when(projectRepository.getProjectByUuid(wrongUUID)).thenReturn(project);

        //when && then
        Assertions.assertThatThrownBy(() -> projectPersistence.getProjectByUUID(wrongUUID))
                .isInstanceOf(ProjectNotFoundException.class);
    }

    @Test
    public void shouldGetProperProjectFromRepository(){

        //given
        String uuid = project1.getUuid();
        Optional<Project> project = Optional.of(this.project1);

        when(projectRepository.getProjectByUuid(uuid))
                .thenReturn(project);

        //when
        Project projectByUUID = projectPersistence.getProjectByUUID(uuid);

        //then
        Assertions.assertThat(projectByUUID).isEqualToComparingFieldByField(project1);
    }

    @Test
    public void shouldCreatedNewProjectProperly(){

        //given
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Optional<User> user = Optional.ofNullable(this.user1);
        String username = this.user1.getUsername();

        when(userRepository.findUserByUsername(username)).thenReturn(user);
        when(projectRepository.save(this.project1)).thenReturn(this.project1);

        //when
        Project savedProject = projectPersistence.saveProject(this.project1);

        //then
        Assertions.assertThat(savedProject)
                .isNotNull();

        Assertions.assertThat(savedProject.getUuid())
                .isNotNull()
                .isNotEmpty();
    }
}