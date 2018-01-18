package com.matuszak.engineer.domain.project.model.dto;

import com.matuszak.engineer.domain.profile.model.Name;
import com.matuszak.engineer.domain.profile.model.Avatar;
import com.matuszak.engineer.domain.project.model.ProjectRole;
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
    private Avatar avatar;
}
