package com.matuszak.engineer.domain.project.model.dto;

import com.matuszak.engineer.domain.project.model.ParticipantId;
import com.matuszak.engineer.domain.project.model.ParticipantLevel;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString
public class ParticipantDTO {
    private ParticipantId userId;
    private ParticipantLevel level;
}
