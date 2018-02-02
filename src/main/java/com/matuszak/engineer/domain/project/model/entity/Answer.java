package com.matuszak.engineer.domain.project.model.entity;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Answer {
    private String author;
    private String message;
}
