package com.matuszak.engineer.domain.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class Skill {
    private String name;
}
