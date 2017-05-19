package com.matuszak.projects.domain;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by dawid on 07.03.17.
 */
@Entity
public class Project {

    public Project() {
    }

    public Project(String name, User owner, String description, String technologies) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.technologies = technologies;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String name;

    @OneToOne
    @NotNull
    private User owner;

    @NotEmpty
    private String description;

    @NotEmpty
    private String technologies;

    private Double price;

    @ManyToMany
    private List<UserDetails> participants;

    @OneToMany
    @JoinColumn(name = "projectId")
    private List<Task> tasks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<UserDetails> getParticipants() {
        return participants;
    }

    public void setParticipants(List<UserDetails> participants) {
        this.participants = participants;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
