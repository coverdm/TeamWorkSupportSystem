package com.matuszak.projects.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.matuszak.projects.domain.enums.ProgrammingLanguages;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by dawid on 21.03.17.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Column(unique = true)
    private String username;

    @NotEmpty
    private String password;

    @NotNull
    private boolean enabled;

    @ElementCollection
    private List<String> userRoles;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @ManyToMany(mappedBy = "participants")
    private List<Project> projects;

    @ElementCollection
    private List<ProgrammingLanguages> experience;

    public User() {
    }

    public User(String username, String password, boolean enabled, List<String> userRoles, String firstName, String lastName, List<Project> projects, List<ProgrammingLanguages> experience) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.userRoles = userRoles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.projects = projects;
        this.experience = experience;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<String> userRoles) {
        this.userRoles = userRoles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> some) {
        this.projects = some;
    }

    public List<ProgrammingLanguages> getExperience() {
        return experience;
    }

    public void setExperience(List<ProgrammingLanguages> experience) {
        this.experience = experience;
    }
}
