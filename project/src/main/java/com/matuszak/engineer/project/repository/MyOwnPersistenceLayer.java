package com.matuszak.engineer.project.repository;

import org.springframework.stereotype.Component;

@Component
public class MyOwnPersistenceLayer {

//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public Project load(ProjectId projectId){
//
//        Project project = entityManager.find(Project.class, projectId, LockModeType.OPTIMISTIC);
//
//        if (project == null) {
//            throw new ProjectNotFoundException("Project " + projectId.getUuid() + " does not exist");
//        }
//
//        return project;
//    }
//
//    public void save(Project project){
//        entityManager.getTransaction().begin();
//        entityManager.persist(project);
//        entityManager.getTransaction().commit();
//    }

}
