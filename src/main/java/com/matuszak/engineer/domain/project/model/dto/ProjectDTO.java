package com.matuszak.engineer.domain.project.model.dto;

import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.ProjectProperties;
import lombok.*;
import java.util.Collection;
import java.util.UUID;

@Builder
@ToString
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ProjectDTO {
    private ProjectId projectId;
    private ProjectProperties projectProperties;
    private Collection<ParticipantDTO> participants;
}