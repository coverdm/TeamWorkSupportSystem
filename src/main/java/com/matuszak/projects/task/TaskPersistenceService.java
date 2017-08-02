package com.matuszak.projects.task;

import java.util.List;

public interface TaskPersistenceService {

    Task create(String projectUUID, Task task);

    Task getTaskById(Long id);
    List<Task> getTasksFromProject(String uuid);
    void delete(Task task);

}
