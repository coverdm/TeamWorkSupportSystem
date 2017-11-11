package com.matuszak.engineer.domain.project.entity;

import com.matuszak.engineer.domain.project.model.ParticipantLevel;
import com.matuszak.engineer.infrastructure.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Participant extends BaseEntity{

    private Long userID;

    @Enumerated
    private ParticipantLevel level;
}
