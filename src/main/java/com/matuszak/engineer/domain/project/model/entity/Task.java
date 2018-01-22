package com.matuszak.engineer.domain.project.model.entity;

import com.matuszak.engineer.domain.project.model.TaskDifficult;
import com.matuszak.engineer.domain.project.model.TaskId;
import com.matuszak.engineer.domain.project.model.TaskStatus;
import com.matuszak.engineer.domain.project.model.WorkerId;
import lombok.*;
import org.hibernate.annotations.CollectionId;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private TaskId taskId;

    private String title;
    private String description;

    @ElementCollection
    private Collection<WorkerId> workers = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private TaskDifficult difficult;

    private Timestamp created;
    private Timestamp deadline;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    public void addWorker(WorkerId workerId){
        this.status = TaskStatus.IN_PROGRESS;
        this.workers.add(workerId);
    }

    public void addWorkers(Collection<WorkerId> workersIds){
        this.status = TaskStatus.IN_PROGRESS;
        this.workers.addAll(workersIds);
    }

    public void removeWorker(Worker worker){
        this.workers.remove(worker);
    }

    public void removeWorkers(Collection<Worker> workers){
        this.workers.remove(workers);
    }
}