package com.matuszak.engineer.domain.project.repository;

import com.matuszak.engineer.domain.project.model.ParticipantId;
import com.matuszak.engineer.domain.project.model.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, ParticipantId> {
    Optional<Participant> getParticipantByParticipantId(ParticipantId id);
}
