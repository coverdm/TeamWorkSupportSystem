package com.matuszak.projects.services;

import com.matuszak.projects.domain.Project;
import com.matuszak.projects.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by dawid on 12.03.17.
 */
public interface ProjectService {

    List<Project> getProjects();
    void createProject(Project project);
    List<Project> getProjectsByParticipate(User user);
}
