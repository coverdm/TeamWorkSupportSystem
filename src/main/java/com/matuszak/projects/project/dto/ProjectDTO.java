package com.matuszak.projects.project.dto;

import com.matuszak.projects.project.entity.ProjectStatus;

import java.time.LocalDate;

public class ProjectDTO {

    private String uuid;
    private String name;
    private ProjectStatus status;
    private LocalDate createdDate;

    public ProjectDTO() {
    }

    public ProjectDTO(String uuid, String name, ProjectStatus status, LocalDate createdDate) {
        this.uuid = uuid;
        this.name = name;
        this.status = status;
        this.createdDate = createdDate;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
