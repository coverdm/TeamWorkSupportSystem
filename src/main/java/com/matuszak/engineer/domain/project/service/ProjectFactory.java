package com.matuszak.engineer.domain.project.service;

import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.ProjectProperties;
import com.matuszak.engineer.domain.project.model.dto.ProjectDTO;
import com.matuszak.engineer.domain.project.model.entity.Project;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@Log
public class ProjectFactory {

    public Project createProject(ProjectProperties projectProperties) {
        log.info("Creating new project...");
        return new Project(new ProjectId(UUID.randomUUID().toString()), projectProperties);
    }
}
