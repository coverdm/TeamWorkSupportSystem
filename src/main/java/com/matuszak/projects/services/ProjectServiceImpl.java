package com.matuszak.projects.services;

import com.matuszak.projects.domain.Project;
import com.matuszak.projects.domain.User;
import com.matuszak.projects.repositories.ProjectRepository;
import com.matuszak.projects.repositories.UserRepository;
import com.matuszak.projects.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Created by dawid on 12.03.17.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private Logger logger = Logger.getLogger(getClass().getName());
    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Project> getProjects() {
        return null;
    }

    @Override
    public void createProject(Project project) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        logger.info("username: " + username);

        User owner = userRepository.findUserByUsername(username);

        logger.info("owner: " + owner.getUsername());

        project.setOwner(owner);

        projectRepository.save(project);
    }

    @Override
    public List<Project> getProjectsByParticipate(User user) {
        return null;
    }
}
