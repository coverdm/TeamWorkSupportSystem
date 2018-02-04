package com.matuszak.engineer.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectId implements Serializable{
    private String uuid;
}
