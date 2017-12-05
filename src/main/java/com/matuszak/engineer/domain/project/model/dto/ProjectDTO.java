package com.matuszak.engineer.domain.project.model.dto;

import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.ProjectProperties;
import lombok.*;

import java.util.Collection;

@Builder
@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private ProjectId projectId;
    private ProjectProperties projectProperties;
    private Collection<TaskDTO> tasks;
    private Collection<ParticipantDTO> participants;
}