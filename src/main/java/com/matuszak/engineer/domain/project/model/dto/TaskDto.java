package com.matuszak.engineer.domain.project.model.dto;

import com.matuszak.engineer.domain.project.model.TaskDifficult;
import com.matuszak.engineer.domain.project.model.TaskId;
import com.matuszak.engineer.domain.project.model.TaskStatus;
import com.matuszak.engineer.domain.project.model.WorkerId;
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
    private Timestamp created;
    private Timestamp deadline;
    private TaskStatus status;
}
