package com.matuszak.engineer.domain.project.model.dto;

import lombok.*;
import java.util.Collection;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@EqualsAndHashCode
public class ProjectDTO {
    private String uuid;
    private String name;
    private Collection<ParticipantDTO> participants;
}