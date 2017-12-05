package com.matuszak.engineer.domain.project.repository;

import com.matuszak.engineer.domain.project.model.TaskId;
import com.matuszak.engineer.domain.project.model.entity.Participant;
import com.matuszak.engineer.domain.project.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, TaskId>{

    Collection<Task> getTasksByExecutorsIn(Collection<Participant> participants);
    Optional<Task> getTasksByTaskId(TaskId taskId);

}
