package com.matuszak.projects.project;

import java.util.List;

public interface ProjectPersistence {

    List<Project> getProjects();
    List<Project> getProjectsByOwnerUsername(String username);

    Project saveProject(Project project);
    Project getProjectByUUID(String uuid);
}