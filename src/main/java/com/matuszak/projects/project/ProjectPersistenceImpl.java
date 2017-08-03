package com.matuszak.projects.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ProjectPersistenceImpl implements ProjectPersistence {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectPersistenceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

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