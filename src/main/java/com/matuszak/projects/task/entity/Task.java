package com.matuszak.projects.task.entity;

import com.matuszak.projects.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    @JoinColumn(name = "employee_Id")
    @NotNull
    private User employee;

    @NotNull
    private String description;

    @NotNull
    private LocalDate deadline;
}
