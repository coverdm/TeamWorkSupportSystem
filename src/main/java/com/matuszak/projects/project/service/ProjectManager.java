package com.matuszak.projects.project.service;

import com.matuszak.projects.project.entity.ProjectStatus;
import com.matuszak.projects.project.entity.Project;
import com.matuszak.projects.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

// I see the class has at least two responsibilities (two reasons to change),
// but i don't know how to separate it efficiently

@Service
public class ProjectManager {

    private final ProjectPersistence projectPersistence;
    private final ProjectCreator projectCreator;
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public ProjectManager(ProjectPersistence projectPersistence, ProjectCreator projectCreator) {
        this.projectPersistence = projectPersistence;
        this.projectCreator = projectCreator;
    }

    public Project changeStatus(String projectUUID, ProjectStatus status) {

        Project project = this.projectPersistence.getProjectByUUID(projectUUID);
        project.setStatus(status);

        return project;
    }

    public Project addParticipants(String projectUUID, List<User> candidates) {

        Project project = this.projectPersistence.getProjectByUUID(projectUUID);
        List<User> participants = project.getParticipants();
        candidates.forEach(participants::add);

        logger.info("Added participants: " + candidates.toString() + " to project: " + projectUUID);

        return this.projectPersistence.saveProject(project);
    }

    public Project create(Project project) {
        return projectPersistence.saveProject(projectCreator.create(project));
    }
}