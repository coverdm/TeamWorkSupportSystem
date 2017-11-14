package com.matuszak.engineer.domain.project.model;

import com.matuszak.engineer.infrastructure.entity.UserId;
import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantId implements Serializable {
    private Long id;

    public ParticipantId(UserId userId) {
        this.id = userId.getId();
    }
}