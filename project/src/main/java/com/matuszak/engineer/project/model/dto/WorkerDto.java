package com.matuszak.engineer.project.model.dto;

import com.matuszak.engineer.project.model.ProjectRole;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString
public class WorkerDto {
    private String workerId;
    private Name name;
    private ProjectRole projectRole;
    private String avatar;
}
