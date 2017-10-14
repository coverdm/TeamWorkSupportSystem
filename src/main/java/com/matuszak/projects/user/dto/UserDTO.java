package com.matuszak.projects.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.matuszak.projects.auth.util.SecurityRole;
import com.matuszak.projects.project.dto.ProjectDTO;
import com.matuszak.projects.project.entity.Project;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class UserDTO{

    private String email;
    private String username;
    private String password;

    private SecurityRole userSecurityRole;

    @JsonIgnoreProperties("participants")
    private List<ProjectDTO> projects;

    @JsonIgnore
    public String getPassword() {
        return password;
    }
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}