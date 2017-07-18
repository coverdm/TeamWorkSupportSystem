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
import java.time.LocalDate;
import java.util.List;

/**
 * Created by dawid on 07.03.17.
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Project {

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

    @NotEmpty
    private String description;

    private LocalDate createdDate;

    private Double price;

    @NotEmpty
    @ElementCollection
    private List<ProgrammingLanguages> technologies;

    @ManyToMany
    private List<User> participants;

    @OneToMany(mappedBy = "employee")
    private List<Task> tasks;
}
