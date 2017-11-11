package com.matuszak.engineer.domain.project.repository;

import com.matuszak.engineer.domain.project.entity.Participant;
import com.matuszak.engineer.domain.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> getProjectByUuid(String Uuid);
    Stream<List<Project>> getProjectsByParticipants(Collection<Participant> participantList);
}
