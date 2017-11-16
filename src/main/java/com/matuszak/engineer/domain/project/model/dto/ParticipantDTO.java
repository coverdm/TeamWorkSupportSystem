package com.matuszak.engineer.domain.project.model.dto;

import com.matuszak.engineer.domain.project.model.ParticipantLevel;
import com.matuszak.engineer.infrastructure.entity.UserId;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString
public class ParticipantDTO {
    private UserId userId;
    private ParticipantLevel level;
}
