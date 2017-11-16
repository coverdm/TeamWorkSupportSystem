package com.matuszak.engineer.domain.project.model;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProjectProperties {
    String name;
    String description;
}
