package com.matuszak.engineer.project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ProjectFinder {

//    @PersistenceContext
//    private EntityManager entityManager;

//    Collection<ProjectDTO> findProjectsByWorkersIn(String userId){
//
//
//
//
//    }

}
