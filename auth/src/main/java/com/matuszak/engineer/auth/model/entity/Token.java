package com.matuszak.engineer.auth.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document
public class Token{

    @Id
    private Long id;

    private String value;
    public Token(String value) {
        this.value = value;
    }
}
