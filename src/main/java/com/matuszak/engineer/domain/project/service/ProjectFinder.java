package com.matuszak.engineer.domain.project.service;

import com.matuszak.engineer.domain.project.model.dto.ProjectDTO;
import com.matuszak.engineer.domain.project.model.entity.Project;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ProjectFinder {

    @PersistenceContext
    private EntityManager entityManager;

//    Collection<ProjectDTO> findProjectsByWorkersIn(String userId){
//
//
//
//
//    }

}
