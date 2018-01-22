package com.matuszak.engineer.domain.project.repository;

import com.matuszak.engineer.domain.project.exceptions.ProjectNotFoundException;
import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.entity.Project;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

@Component
public class MyOwnPersistenceLayer {

    @PersistenceContext
    private EntityManager entityManager;

    public Project load(ProjectId projectId){

        Project project = entityManager.find(Project.class, projectId, LockModeType.OPTIMISTIC);

        if (project == null) {
            throw new ProjectNotFoundException("Project " + projectId.getUuid() + " does not exist");
        }

        return project;
    }

    public void save(Project project){
        entityManager.getTransaction().begin();
        entityManager.persist(project);
        entityManager.getTransaction().commit();
    }

}
