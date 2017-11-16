package com.matuszak.engineer.domain.auth.model.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class Token{

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;
    private String value;

    public Token(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
