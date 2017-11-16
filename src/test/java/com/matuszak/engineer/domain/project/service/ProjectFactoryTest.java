package com.matuszak.engineer.domain.project.service;


import com.matuszak.engineer.Application;
import com.matuszak.engineer.domain.project.model.ProjectProperties;
import com.matuszak.engineer.domain.project.model.dto.ProjectDTO;
import com.matuszak.engineer.domain.project.model.entity.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = Application.class)
@RunWith(SpringRunner.class)
public class ProjectFactoryTest {

    @Autowired
    ProjectFactory projectFactory;

    @Test
    public void shouldCreateNewProject(){

        //given
        ProjectProperties projectProperties = new ProjectProperties("someProject", "someDescription");

        //then
        Project project = projectFactory.createProject(projectProperties);

        assertThat(project).isNotNull();
        assertThat(project.getProjectProperties()).isEqualToComparingFieldByField(projectProperties);
        assertThat(project.getProjectId()).isNotNull();
    }
}
