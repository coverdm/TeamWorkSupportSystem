package com.matuszak.engineer.domain.project.model.entity;

import com.matuszak.engineer.domain.project.model.TaskDifficult;
import com.matuszak.engineer.domain.project.model.TaskId;
import com.matuszak.engineer.domain.project.model.TaskStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private TaskId taskId;

    private String title;
    private String description;

    @OneToMany
    private Collection<Worker> workers = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private TaskDifficult difficult;

    private Date created;
    private Date deadline;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    public Task(String title, String description, TaskDifficult difficult, Date deadline) {
        this.title = title;
        this.description = description;
        this.difficult = difficult;
        this.deadline = deadline;
        this.created = Date.valueOf(LocalDate.now());
        this.status = TaskStatus.TODO;
        this.taskId = new TaskId(TaskId.generateShortId());
    }

    public void addWorker(Worker worker){
        this.status = TaskStatus.IN_PROGRESS;
        this.workers.add(worker);
    }

    public void addWorkers(Collection<Worker> workers){
        this.status = TaskStatus.IN_PROGRESS;
        this.workers.addAll(workers);
    }

    public void removeWorker(Worker worker){
        this.workers.remove(worker);
    }

    public void removeWorkers(Collection<Worker> workers){
        this.workers.remove(workers);
    }
}