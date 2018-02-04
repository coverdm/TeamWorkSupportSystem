package com.matuszak.engineer.project.repository;

import com.matuszak.engineer.project.model.ProjectId;
import com.matuszak.engineer.project.model.entity.Project;
import com.matuszak.engineer.project.model.entity.Worker;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Optional;

public interface ProjectRepository extends MongoRepository<Project, ProjectId> {
    Optional<Project> getProjectByProjectId(ProjectId projectId);
    Collection<Project> findProjectsByWorkersIn(Collection<Worker> workers);
}
