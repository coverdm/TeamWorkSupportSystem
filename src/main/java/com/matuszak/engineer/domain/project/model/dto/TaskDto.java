package com.matuszak.engineer.domain.project.model.dto;

import com.matuszak.engineer.domain.project.model.TaskDifficult;
import com.matuszak.engineer.domain.project.model.TaskId;
import com.matuszak.engineer.domain.project.model.TaskStatus;
import lombok.*;

import java.sql.Date;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
@ToString
@Setter
public class TaskDto {
    private TaskId taskId;
    private String title;
    private String description;
    private Collection<WorkerDto> workers;
    private TaskDifficult difficult;
    private Date created;
    private Date deadline;
    private TaskStatus status;
}
