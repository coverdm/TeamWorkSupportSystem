package com.matuszak.projects.project;

import com.matuszak.projects.user.UserService;
import com.matuszak.projects.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private final ProjectRepository projectRepository;
    private final UserService userService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    @Override
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project saveProject(Project project) {
        project.setUuid(generateUUID());
        this.userService.getUserByUsername(this.getLoggedUsername()).ifPresent(user -> project.setOwner(user));

        logger.info(project.toString());

        return projectRepository.save(project);
    }

    @Override
    public List<Project> getProjectsByOwnerUsername(String username) {
        return projectRepository.getProjectsByOwnerUsername(username);
    }

    @Override
    public Project addParticipants(String uuid, List<User> users) {

        Optional<Project> projectByUUID = this.getProjectByUUID(uuid);

        projectByUUID.ifPresent(e -> e.getParticipants().addAll(users));

        return projectByUUID.orElse(null);
    }

    @Override
    public Optional<Project> getProjectByUUID(String uuid) {
        return projectRepository.getProjectByUuid(uuid);
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }

    private String getLoggedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}