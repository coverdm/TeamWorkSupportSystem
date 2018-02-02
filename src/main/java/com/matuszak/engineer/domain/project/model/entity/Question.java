package com.matuszak.engineer.domain.project.model.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    private String author;
    private String message;

}
