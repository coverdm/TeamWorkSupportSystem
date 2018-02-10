package com.matuszak.engineer.project.model.dto;

import com.matuszak.engineer.project.model.ProjectRole;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class HireModel {
    private String userId;
    private ProjectRole projectRole;
}
