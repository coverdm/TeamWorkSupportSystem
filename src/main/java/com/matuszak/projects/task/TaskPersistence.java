package com.matuszak.projects.task;

import java.util.List;

public interface TaskPersistence {

    Task create(String projectUUID, Task task);

    Task getTaskById(Long id);
    List<Task> getTasksFromProject(String uuid);
    void delete(Task task);

}
