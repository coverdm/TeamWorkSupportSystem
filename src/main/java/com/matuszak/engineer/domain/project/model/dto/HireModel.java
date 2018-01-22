package com.matuszak.engineer.domain.project.model.dto;

import com.matuszak.engineer.domain.project.model.ProjectRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HireModel {
    private String userId;
    private ProjectRole projectRole;
}
