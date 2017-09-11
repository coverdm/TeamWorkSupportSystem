package com.matuszak.projects.task.service;

import com.matuszak.projects.task.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskPersistence {

    Task create(String projectUUID, Task task);

    Optional<Task> getTaskById(Long id);
    List<Task> getTasksFromProject(String uuid);
    void delete(Task task);

}
