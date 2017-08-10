package com.matuszak.projects.project.repository;

import com.matuszak.projects.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> getProjectsByOwnerUsername(String username);
    Optional<Project> getProjectByUuid(String uuid);
}
