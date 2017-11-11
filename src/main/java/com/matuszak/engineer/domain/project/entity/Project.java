package com.matuszak.engineer.domain.project.entity;

import com.matuszak.engineer.infrastructure.entity.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor
public class Project extends BaseEntity {

    @Column(unique = true)
    private String uuid;
    private String name;

    @OneToMany
    private Collection<Participant> participants;

    public Project(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.participants = new ArrayList<>();
    }

    public void addParticipant(Participant participant){
        participants.add(participant);
    }
}
