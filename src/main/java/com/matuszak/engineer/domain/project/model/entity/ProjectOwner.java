package com.matuszak.engineer.domain.project.model.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ProjectOwner {
    private String id;
}
