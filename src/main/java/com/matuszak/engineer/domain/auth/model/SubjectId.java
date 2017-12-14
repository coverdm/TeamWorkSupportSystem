package com.matuszak.engineer.domain.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Embeddable
@AllArgsConstructor
public class SubjectId implements Serializable {
    private String email;
}