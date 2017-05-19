package com.matuszak.projects.repositories;

import com.matuszak.projects.domain.Project;
import com.matuszak.projects.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by dawid on 12.03.17.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p")
    List<Project> getProjectsByParticipions(Long participant_id);

}
