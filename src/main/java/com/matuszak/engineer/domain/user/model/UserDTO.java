package com.matuszak.engineer.domain.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.matuszak.engineer.domain.project.model.dto.ProjectDTO;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class UserDTO{

    private String email;

    //TODO check does the property is needed
    @JsonIgnoreProperties("participants")
    private List<ProjectDTO> projects;
}