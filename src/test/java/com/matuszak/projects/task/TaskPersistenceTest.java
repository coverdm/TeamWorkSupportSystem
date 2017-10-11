package com.matuszak.projects.task;

import com.matuszak.projects.Application;
import com.matuszak.projects.TestConfiguration;
import com.matuszak.projects.auth.util.SecurityRole;
import com.matuszak.projects.project.entity.Project;
import com.matuszak.projects.project.repository.ProjectRepository;
import com.matuszak.projects.project.service.ProjectPersistence;
import com.matuszak.projects.project.entity.ProjectStatus;
import com.matuszak.projects.task.entity.Task;
import com.matuszak.projects.task.repository.TaskRepository;
import com.matuszak.projects.task.service.TaskPersistence;
import com.matuszak.projects.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
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
                .securityRole(SecurityRole.USER)
                .email("someEmail@gmail.com")
                .enabled(true)
                .build();

        User user2 = User.builder()
                .username("2Username")
                .password("2Password")
                .securityRole(SecurityRole.USER)
                .email("s2mail@gmail.com")
                .enabled(true)
                .build();

        Project project = Project.builder()
                .name("someProject")
                .uuid(UUID.randomUUID().toString())
                .owner(user)
                .participants(new ArrayList<>())
                .description("someDescription")
                .createdDate(LocalDate.now())
                .status(ProjectStatus.FINISHED)
                .build();

        Task firstTask = Task.builder()
                .deadline(LocalDate.now())
                .description("First task")
                .employee(user2)
                .build();

        when(taskRepository.save(firstTask)).thenReturn(firstTask);
        when(projectRepository.getProjectByUuid(project.getUuid())).thenReturn(Optional.of(project));

        Task task = taskPersistence.create(project.getUuid(), firstTask);

        Assertions.assertThat(firstTask).isEqualToComparingFieldByField(task);
    }
}
