package com.matuszak.engineer.domain.auth.model.entity;

import com.matuszak.engineer.infrastructure.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Token extends BaseEntity{

    private String value;
    public Token(String value) {
        this.value = value;
    }
}
