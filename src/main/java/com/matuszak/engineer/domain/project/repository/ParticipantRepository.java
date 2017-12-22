package com.matuszak.engineer.domain.project.repository;

import com.matuszak.engineer.domain.project.model.entity.Participant;
import com.matuszak.engineer.infrastructure.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Collection<Participant> getParticipantByUserId(UserId userId);
}
