package com.matuszak.projects.project;

import com.matuszak.projects.Application;
import com.matuszak.projects.TestConfiguration;
import com.matuszak.projects.authorization.Role;
import com.matuszak.projects.user.User;
import com.matuszak.projects.user.UserRepository;
import com.matuszak.projects.user.UserPersistence;
import com.matuszak.projects.util.ProgrammingLanguages;
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

    @Autowired private UserPersistence userPersistence;
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
                .experience(Arrays.asList(ProgrammingLanguages.JAVA, ProgrammingLanguages.PHP))
                .enabled(true)
                .firstName("someFirstName")
                .lastName("someLastName")
                .build();

        this.user2 = User.builder()
                .username("2Username")
                .password("2Password")
                .userRole(Role.USER)
                .email("s2mail@gmail.com")
                .experience(Arrays.asList(ProgrammingLanguages.JAVA, ProgrammingLanguages.PHP))
                .enabled(true)
                .firstName("someF2stName")
                .lastName("som2tName")
                .build();

        this.project1 = Project.builder()
                .name("someProject")
                .uuid(UUID.randomUUID().toString())
                .owner(this.user1)
                .participants(new ArrayList<>())
                .technologies(null)
                .description("someDescription")
                .createdDate(LocalDate.now())
                .status(ProjectStatus.FINISHED)
                .build();

        this.project2 = Project.builder()
                .name("secondProject")
                .uuid(UUID.randomUUID().toString())
                .owner(this.user2)
                .participants(new ArrayList<>())
                .technologies(null)
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
    public void should_Return_Only_Owned_Projects(){

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
    public void should_Return_List_Of_Projects(){

        //given
        List<Project> projects = Arrays.asList(this.project1, this.project2);

        when(projectRepository.findAll()).thenReturn(projects);

        //when
        List<Project> gotProjects = this.projectPersistence.getProjects();

        //then
        Assertions.assertThat(projects).isEqualTo(gotProjects);
    }

    @Test
    public void should_Add_New_Participants(){

        // given
        String uuid = project1.getUuid();
        Optional<Project> project = Optional.of(project1);
        List<User> newParticipants = Arrays.asList(user1, user2);

        when(projectRepository.getProjectByUuid(uuid)).thenReturn(project);

        when(projectRepository.save(project1)).thenReturn(project1);

        // when
        Project updatedProject = projectPersistence.addParticipants(uuid, newParticipants);

        // then
        Assertions.assertThat(project1).isEqualToComparingFieldByField(updatedProject);
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

    @Test
    public void shouldChangeProjectsStatus(){

        //given
        Optional<Project> project = Optional.of(this.project1);
        String uuid = this.project1.getUuid();

        when(projectRepository.save(this.project1)).thenReturn(this.project1);
        when(projectRepository.getProjectByUuid(uuid)).thenReturn(project);

        //when
        Project updatedProject = projectPersistence.changeStatus(uuid, ProjectStatus.FINISHED);

        //then
        Assertions.assertThat(updatedProject.getStatus()).isEqualTo(ProjectStatus.FINISHED);
    }

//    @PropertySource("classpath:test.properties")
//    @Configuration
//    protected static class TestConfiguration{}

}