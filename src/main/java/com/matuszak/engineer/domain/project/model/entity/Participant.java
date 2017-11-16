package com.matuszak.engineer.domain.project.model.entity;

import com.matuszak.engineer.domain.project.model.ParticipantId;
import com.matuszak.engineer.domain.project.model.ParticipantLevel;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Participant implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private ParticipantId participantId;

    @Enumerated
    private ParticipantLevel level;

    private Participant() { // just for hibernate
    }

    public Participant(ParticipantId participantId, ParticipantLevel level) {
        this.participantId = participantId;
        this.level = level;
    }
}

