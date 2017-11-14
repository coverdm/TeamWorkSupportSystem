package com.matuszak.engineer.domain.project.model.entity;

import com.matuszak.engineer.domain.project.model.ParticipantId;
import com.matuszak.engineer.domain.project.model.ParticipantLevel;
import com.matuszak.engineer.infrastructure.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Enumerated;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Participant{

    @EmbeddedId
    private ParticipantId participantId;

    @Enumerated
    private ParticipantLevel level;
}

