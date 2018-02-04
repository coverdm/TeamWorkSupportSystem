package com.matuszak.engineer.project.model.dto;

import com.matuszak.engineer.project.model.ProjectId;
import com.matuszak.engineer.project.model.ProjectProperties;
import lombok.*;

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
}