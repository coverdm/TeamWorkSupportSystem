package com.matuszak.engineer.auth.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document
@ToString
public class Token{

    @Id
    private String value;
    public Token(String value) {
        this.value = value;
    }
}
