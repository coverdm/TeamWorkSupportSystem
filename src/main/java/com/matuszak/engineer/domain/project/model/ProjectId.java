package com.matuszak.engineer.domain.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

//@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectId implements Serializable{
    private String uuid;
}
