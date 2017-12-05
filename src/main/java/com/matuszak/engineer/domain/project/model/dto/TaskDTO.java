package com.matuszak.engineer.domain.project.model.dto;

import com.matuszak.engineer.domain.project.model.TaskId;
import com.matuszak.engineer.domain.project.model.TaskProperties;
import com.matuszak.engineer.domain.project.model.TaskStatus;
import com.matuszak.engineer.domain.project.model.entity.Participant;
import lombok.*;

import java.util.Collection;

@ToString
@EqualsAndHashCode
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private TaskId taskId;
    private TaskProperties taskProperties;
    private TaskStatus taskStatus;
    private Collection<Participant> executors;
}
