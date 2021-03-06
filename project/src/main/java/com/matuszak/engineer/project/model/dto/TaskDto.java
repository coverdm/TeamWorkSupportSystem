package com.matuszak.engineer.project.model.dto;

import com.matuszak.engineer.project.model.TaskDifficult;
import com.matuszak.engineer.project.model.TaskStatus;
import com.matuszak.engineer.project.model.WorkerId;
import lombok.*;

import java.sql.Timestamp;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
@ToString
@Setter
public class TaskDto {
    private String taskId;
    private String title;
    private String description;
    private Collection<WorkerId> workers;
    private TaskDifficult difficult;
    private String created;
    private String deadline;
    private TaskStatus status;
}
