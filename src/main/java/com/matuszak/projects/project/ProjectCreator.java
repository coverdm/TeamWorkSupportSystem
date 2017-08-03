package com.matuszak.projects.project;

import com.matuszak.projects.user.User;
import com.matuszak.projects.user.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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

    public Project create(Project project){

        User owner = userPersistence.getUserByUsername(getLoggedUsername());

        project.setUuid(UUID.randomUUID().toString());

        project.setOwner(owner);

        project.setCreatedDate(LocalDate.now());

        logger.info("Created project: " + project.toString());

        return project;

    }

    private String getLoggedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
