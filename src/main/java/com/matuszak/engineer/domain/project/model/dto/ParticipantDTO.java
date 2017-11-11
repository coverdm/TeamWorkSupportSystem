package com.matuszak.engineer.domain.project.model.dto;

import com.matuszak.engineer.domain.project.model.ParticipantLevel;
import com.matuszak.engineer.domain.user.model.UserDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString
public class ParticipantDTO {
    private Long id;
    private UserDTO user;
    private ParticipantLevel level;
}
