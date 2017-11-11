package com.matuszak.engineer.domain.user.entity;

import com.matuszak.engineer.domain.project.entity.Project;
import com.matuszak.engineer.infrastructure.entity.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Data
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{

    private String email;
    private String username;

    @ManyToMany(mappedBy = "participants")
    private Collection<Project> projects;
}