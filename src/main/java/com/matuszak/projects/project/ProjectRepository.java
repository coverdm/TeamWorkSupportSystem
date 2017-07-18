package com.matuszak.projects.project;

import com.matuszak.projects.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by dawid on 12.03.17.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> getProjectsByOwnerUsername(String username);

    Optional<Project> getProjectByUuid(String uuid);
}
