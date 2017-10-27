package com.matuszak.projects.project.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ProjectDTO {

    private String uuid;
    private String name;
    private String description;
    private ProjectStatus status;
    private LocalDate createdDate;
    private List<ParticipantDTO> participantDTOS;
}