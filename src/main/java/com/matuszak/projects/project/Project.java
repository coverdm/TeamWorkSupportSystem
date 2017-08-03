package com.matuszak.projects.project;

import com.matuszak.projects.util.ProgrammingLanguages;
import com.matuszak.projects.task.Task;
import com.matuszak.projects.user.User;
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

    @NotEmpty
    @ElementCollection
    private List<ProgrammingLanguages> technologies;

    @ManyToMany
    private List<User> participants;

    @OneToMany(mappedBy = "employee")
    private List<Task> tasks;
}
