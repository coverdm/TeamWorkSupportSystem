package com.matuszak.projects.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CollectionType;
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

    @OneToOne
    @JoinColumn(name = "userDetailsId")
    private UserDetails userDetails;

    @ElementCollection
    private List<String> userRoles;

    public User() {
    }

    public User(String username, String password, boolean enabled, UserDetails userDetails, List<String> userRoles) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.userDetails = userDetails;
        this.userRoles = userRoles;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<String> userRoles) {
        this.userRoles = userRoles;
    }
}
