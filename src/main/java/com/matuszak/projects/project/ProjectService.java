package com.matuszak.projects.project;

import com.matuszak.projects.user.User;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<Project> getProjects();
    Project saveProject(Project project);
    List<Project> getProjectsByOwnerUsername(String username);
    Project addParticipants(String uuid, List<User> users);
    Optional<Project> getProjectByUUID(String uuid);
}