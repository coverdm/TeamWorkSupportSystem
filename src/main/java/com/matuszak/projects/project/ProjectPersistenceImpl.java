package com.matuszak.projects.project;

import com.matuszak.projects.user.UserPersistence;
import com.matuszak.projects.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class ProjectPersistenceImpl implements ProjectPersistence {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private final ProjectRepository projectRepository;
    private final UserPersistence userPersistence;

    @Autowired
    public ProjectPersistenceImpl(ProjectRepository projectRepository, UserPersistence userPersistence) {
        this.projectRepository = projectRepository;
        this.userPersistence = userPersistence;
    }

    @Override
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project saveProject(Project project) {

        project.setUuid(UUID.randomUUID().toString());

        String loggedUsername = getLoggedUsername();

        User owner = userPersistence.getUserByUsername(loggedUsername);

        project.setOwner(owner);

        project.setCreatedDate(LocalDate.now());

        logger.info("Created project: " + project.toString());

        return projectRepository.save(project);
    }

    @Override
    public List<Project> getProjectsByOwnerUsername(String username) {
        return projectRepository.getProjectsByOwnerUsername(username);
    }

    @Override
    public Project addParticipants(String uuid, List<User> users) {

        Project projectByUUID = null;

        try{

            projectByUUID = this.getProjectByUUID(uuid);
            List<User> participants = projectByUUID.getParticipants();
            users.forEach(participants::add);

        }catch(ProjectNotFoundException e){
            e.printStackTrace();
        }

        logger.info("Added participants: " + users.toString() + " to project: " + uuid);

        return this.projectRepository.save(projectByUUID);
    }

    @Override
    public Project getProjectByUUID(String uuid) throws ProjectNotFoundException{
        return this.projectRepository.getProjectByUuid(uuid)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));
    }

    private String getLoggedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public Project changeStatus(String uuid, ProjectStatus status) {

        Project projectByUUID = null;

        try{
            projectByUUID = getProjectByUUID(uuid);
            projectByUUID.setStatus(status);
        }catch (ProjectNotFoundException e){
            e.printStackTrace();
        }

        return this.projectRepository.save(projectByUUID);
    }

    @Override
    public Project finishProject(String uuid) {

        Project projectByUUID = getProjectByUUID(uuid);
        projectByUUID.setStatus(ProjectStatus.FINISHED);
        return projectByUUID;
    }
}