package com.matuszak.engineer.project.service;

import com.matuszak.engineer.project.model.ProjectId;
import com.matuszak.engineer.project.model.ProjectProperties;
import com.matuszak.engineer.project.model.entity.Project;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Log
public class ProjectFactory {

    public Project createProject(ProjectProperties projectProperties) {
        return new Project(new ProjectId(UUID.randomUUID().toString()), projectProperties);
    }
}
