package com.matuszak.engineer.domain.project.repository;

import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.entity.Participant;
import com.matuszak.engineer.domain.project.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface ProjectRepository extends JpaRepository<Project, ProjectId> {
    Optional<Project> getProjectByProjectId(ProjectId Uuid);
}
