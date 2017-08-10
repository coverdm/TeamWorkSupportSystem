package com.matuszak.projects.project.service;

import com.matuszak.projects.project.entity.Project;
import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.user.service.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

@Component
public class ProjectCreator {

    private final UserPersistence userPersistence;
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public ProjectCreator(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    public Project create(Project project) {

        User owner = userPersistence.getUserByUsername(getLoggedUsername());
        project.setOwner(owner);
        init(project);

        return project;

    }

    private void init(Project project) {
        project.setUuid(UUID.randomUUID().toString());
        project.setCreatedDate(LocalDate.now());
        project.setParticipants(new ArrayList<>());
        project.setTasks(new ArrayList<>());
    }

    private String getLoggedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}