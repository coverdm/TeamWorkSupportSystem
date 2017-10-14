package com.matuszak.projects.project.service;

import com.matuszak.projects.project.entity.Project;
import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log

//TODO
public class ProjectCreator {

    private final UserService userService;

    public Project create(Project project) {

        User owner = userService.getUserByUsername(getLoggedUsername()).get();
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