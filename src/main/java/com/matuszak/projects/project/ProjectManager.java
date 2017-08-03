package com.matuszak.projects.project;

import com.matuszak.projects.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

// I see the class has at least two responsibilities (two reasons to change),
// but i don't know how to separate it efficiently

@Service
public class ProjectManager {

    private final ProjectPersistence projectPersistence;
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public ProjectManager(ProjectPersistence projectPersistence) {
        this.projectPersistence = projectPersistence;
    }

    public Project finishProject(String projectUUID){

        Project project = null;

        try{

            project = this.projectPersistence.getProjectByUUID(projectUUID);
            project.setStatus(ProjectStatus.FINISHED);

        }catch (ProjectNotFoundException e){
            e.printStackTrace();
        }

        return project;
    }

    public Project addParticipants(String projectUUID, List<User> candidates){

        Project project = null;

        try{

            project = this.projectPersistence.getProjectByUUID(projectUUID);
            List<User> participants = project.getParticipants();
            candidates.forEach(participants::add);

            logger.info("Added participants: " + candidates.toString() + " to project: " + projectUUID);

        }catch(ProjectNotFoundException e){
            e.printStackTrace();
        }

        return this.projectPersistence.saveProject(project);
    }
}