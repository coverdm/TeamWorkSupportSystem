package com.matuszak.engineer.domain.project.model.entity;

import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.ProjectProperties;
import com.matuszak.engineer.domain.project.model.ProjectStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
public class Project{

    @EmbeddedId
    private ProjectId projectId;
    private ProjectOwner ownerId;

    private ProjectProperties projectProperties;

    @Enumerated(EnumType.ORDINAL)
    private ProjectStatus projectStatus;

    @OneToMany
    private Collection<Worker> workers;

    @OneToMany
    private Collection<SourceCode> sourceCode;

    private Project() { // just for hibernate
    }

    public Project(ProjectId projectId, ProjectProperties projectProperties) {
        this.projectId = projectId;
        this.projectProperties = projectProperties;
        projectStatus = ProjectStatus.CREATED;
        this.workers = new ArrayList<>();
    }

    public void addParticipant(Worker worker){
        workers.add(worker);
    }

    public void addSourceCode(SourceCode sourceCode){
        this.sourceCode.add(sourceCode);
    }

    public void markAsDeleted() {
        this.projectStatus = ProjectStatus.DELETED;
    }

    public void markAsClosed() {
        this.projectStatus = ProjectStatus.CLOSED;
    }
}