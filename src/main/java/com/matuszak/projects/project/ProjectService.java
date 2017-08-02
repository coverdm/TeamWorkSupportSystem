package com.matuszak.projects.project;

import com.matuszak.projects.user.User;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<Project> getProjects();
    List<Project> getProjectsByOwnerUsername(String username);

    Project saveProject(Project project);
    Project addParticipants(String uuid, List<User> users);
    Project getProjectByUUID(String uuid);

    Project finishProject(String uuid);
    Project changeStatus(String uuid, ProjectStatus status);
}