package com.matuszak.projects.project.service;

import com.matuszak.projects.project.exceptions.ProjectNotFoundException;
import com.matuszak.projects.project.repository.ProjectRepository;
import com.matuszak.projects.project.entity.Project;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Log
public class ProjectPersistenceImpl implements ProjectPersistence {

    private final ProjectRepository projectRepository;

    @Override
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public List<Project> getProjectsByOwnerUsername(String username) {
        return projectRepository.getProjectsByOwnerUsername(username);
    }

    @Override
    public Project getProjectByUUID(String uuid) throws ProjectNotFoundException {
        return this.projectRepository.getProjectByUuid(uuid)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));
    }
}