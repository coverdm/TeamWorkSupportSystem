package com.matuszak.engineer.domain.auth.model.entity;

import com.matuszak.engineer.infrastructure.entity.UserId;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@Getter
public class SubjectId implements Serializable{

    private Long id;

    public SubjectId (UserId id){
        this.id = id.getId();
    }
}
