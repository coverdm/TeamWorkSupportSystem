package com.matuszak.engineer.domain.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
public class UserID {
    private long id;
}
