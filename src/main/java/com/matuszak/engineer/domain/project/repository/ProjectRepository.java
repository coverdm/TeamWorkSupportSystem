package com.matuszak.engineer.domain.project.repository;

import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.entity.Worker;
import com.matuszak.engineer.domain.project.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, ProjectId> {
    Optional<Project> getProjectByProjectId(ProjectId Uuid);
    Collection<Project> findProjectsByWorkersIn(Collection<Worker> worker);
}
