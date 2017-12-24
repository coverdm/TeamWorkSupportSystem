package com.matuszak.engineer.domain.project.model.dto;

import com.matuszak.engineer.domain.project.model.ProjectRole;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString
public class WorkerDto {
    private String userId;
    private ProjectRole projectRole;
}
