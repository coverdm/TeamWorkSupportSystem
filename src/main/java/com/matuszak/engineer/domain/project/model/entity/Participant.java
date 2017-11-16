package com.matuszak.engineer.domain.project.model.entity;

import com.matuszak.engineer.domain.project.model.ParticipantLevel;
import com.matuszak.engineer.infrastructure.entity.UserId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Participant implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UserId userId;

    @Enumerated
    private ParticipantLevel level;

    private Participant() { // just for hibernate
    }

    public Participant(UserId userId, ParticipantLevel level) {
        this.userId = userId;
        this.level = level;
    }
}

