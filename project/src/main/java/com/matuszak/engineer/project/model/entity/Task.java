package com.matuszak.engineer.project.model.entity;

import com.matuszak.engineer.project.model.TaskDifficult;
import com.matuszak.engineer.project.model.TaskId;
import com.matuszak.engineer.project.model.TaskStatus;
import com.matuszak.engineer.project.model.WorkerId;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

@Document
@Data
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class Task {

    @Id
    private Long id;

    private TaskId taskId;

    private String title;
    private String description;

    private Collection<WorkerId> workers = new ArrayList<>();

    private TaskDifficult difficult;

    private Timestamp created;
    private Timestamp deadline;

    private TaskStatus status;

    public void addWorker(WorkerId workerId){
        this.status = TaskStatus.IN_PROGRESS;
        this.workers.add(workerId);
    }

    public void addWorkers(Collection<WorkerId> workersIds){
        this.status = TaskStatus.IN_PROGRESS;
        this.workers.addAll(workersIds);
    }

    public void complete(){
        this.status = TaskStatus.FINISHED;
    }

    public void removeWorker(Worker worker){
        this.workers.remove(worker);
    }

    public void removeWorkers(Collection<Worker> workers){
        this.workers.remove(workers);
    }
}