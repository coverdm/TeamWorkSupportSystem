package com.matuszak.projects.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * Created by dawid on 22.03.17.
 */
@Entity
public class Task {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    @JoinColumn(name = "ownerId")
    @NotNull
    private User owner;

    @OneToOne
    @JoinColumn(name = "employeeId")
    @NotNull
    private User employee;

    @NotNull
    private String description;

    @NotNull
    private Date deadline;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
