package com.matuszak.projects.task;

import com.matuszak.projects.Application;
import com.matuszak.projects.TestConfiguration;
import com.matuszak.projects.authorization.Role;
import com.matuszak.projects.project.Project;
import com.matuszak.projects.project.ProjectRepository;
import com.matuszak.projects.project.ProjectPersistence;
import com.matuszak.projects.project.ProjectStatus;
import com.matuszak.projects.user.User;
import com.matuszak.projects.util.ProgrammingLanguages;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, TestConfiguration.class})
public class TaskPersistenceTest {

    @MockBean
    TaskRepository taskRepository;

    @MockBean
    ProjectRepository projectRepository;

    @Autowired
    ProjectPersistence projectPersistence;

    @Autowired
    TaskPersistence taskPersistence;

    @Test
    public void shouldPersistNewTask(){

        User user = User.builder()
                .username("firsUser")
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

        Project project = Project.builder()
                .name("someProject")
                .uuid(UUID.randomUUID().toString())
                .owner(user)
                .participants(new ArrayList<>())
                .technologies(null)
                .description("someDescription")
                .createdDate(LocalDate.now())
                .status(ProjectStatus.FINISHED)
                .build();

        Task firstTask = Task.builder()
                .deadline(LocalDate.now())
                .description("First task")
                .owner(user)
                .employee(user2)
                .build();

        when(taskRepository.save(firstTask)).thenReturn(firstTask);
        when(projectRepository.getProjectByUuid(project.getUuid())).thenReturn(Optional.of(project));

        Task task = taskPersistence.create(project.getUuid(), firstTask);

        Assertions.assertThat(firstTask).isEqualToComparingFieldByField(task);
    }
}
