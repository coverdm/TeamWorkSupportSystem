package com.matuszak.engineer.domain.project.model.entity;

import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.ProjectProperties;
import com.matuszak.engineer.domain.project.model.ProjectStatus;
import com.matuszak.engineer.infrastructure.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Data
@Entity
public class Project{

    @EmbeddedId
    private ProjectId projectId;
    private ProjectProperties projectProperties;

    @Enumerated(EnumType.ORDINAL)
    private ProjectStatus projectStatus;

    @OneToMany
    private Collection<Participant> participants;

    public Project() {
        this.participants = new ArrayList<>();
    }

    public Project(ProjectId projectId, ProjectProperties projectProperties) {
        this.projectId = projectId;
        this.projectProperties = projectProperties;
        projectStatus = ProjectStatus.CREATED;
        this.participants = new ArrayList<>();
    }

    public void addParticipant(Participant participant){
        participants.add(participant);
    }

    public void markAsDeleted() {
        this.projectStatus = ProjectStatus.DELETED;
    }

    public void markAsClosed() {
        this.projectStatus = ProjectStatus.CLOSED;
    }
}
