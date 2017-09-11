package com.matuszak.projects.project.service;

import com.matuszak.projects.project.entity.ProjectStatus;
import com.matuszak.projects.project.entity.Project;
import com.matuszak.projects.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Log
public class ProjectManager {

    private final ProjectPersistence projectPersistence;
    private final ProjectCreator projectCreator;

    public Project changeStatus(String projectUUID, ProjectStatus status) {

        Project project = this.projectPersistence.getProjectByUUID(projectUUID);
        project.setStatus(status);

        return project;
    }

    public Project addParticipants(String projectUUID, List<User> candidates) {

        Project project = this.projectPersistence.getProjectByUUID(projectUUID);
        List<User> participants = project.getParticipants();
        candidates.forEach(participants::add);

        log.info("Added participants: " + candidates.toString() + " to project: " + projectUUID);

        return this.projectPersistence.saveProject(project);
    }

    public Project create(Project project) {
        return projectPersistence.saveProject(projectCreator.create(project));
    }
}