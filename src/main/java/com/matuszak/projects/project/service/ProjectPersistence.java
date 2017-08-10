package com.matuszak.projects.project.service;

import com.matuszak.projects.project.entity.Project;

import java.util.List;

public interface ProjectPersistence {

    List<Project> getProjects();
    List<Project> getProjectsByOwnerUsername(String username);

    Project saveProject(Project project);
    Project getProjectByUUID(String uuid);
}