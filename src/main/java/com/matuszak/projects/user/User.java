package com.matuszak.projects.user;

import com.matuszak.projects.project.Project;
import com.matuszak.projects.util.ProgrammingLanguages;
import com.matuszak.projects.authorization.Role;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotEmpty
    @Column(unique = true)
    private String username;

    @NotEmpty
    private String password;

    @NotNull
    private boolean enabled;

    @NotNull
    @Enumerated
    private Role userRole;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @ManyToMany(mappedBy = "participants")
    private List<Project> projects;

    @ElementCollection
    private List<ProgrammingLanguages> experience;
}
