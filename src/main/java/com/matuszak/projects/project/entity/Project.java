package com.matuszak.projects.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.matuszak.projects.task.entity.Task;
import com.matuszak.projects.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Project implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String uuid;

    @NotEmpty
    @Column(unique = true)
    private String name;

    @OneToOne
    @NotNull
    private User owner;

    @Enumerated
    @NotNull
    private ProjectStatus status;

    @NotNull
    private String description;

    @NotNull
    private LocalDate createdDate;

    @ManyToMany
    @JsonIgnoreProperties("projects")
    private List<User> participants;

    @JsonIgnoreProperties
    @OneToMany(mappedBy = "employee")
    private List<Task> tasks;
}
