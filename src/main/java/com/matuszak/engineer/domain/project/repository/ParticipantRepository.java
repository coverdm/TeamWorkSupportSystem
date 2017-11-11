package com.matuszak.engineer.domain.project.repository;

import com.matuszak.engineer.domain.project.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
