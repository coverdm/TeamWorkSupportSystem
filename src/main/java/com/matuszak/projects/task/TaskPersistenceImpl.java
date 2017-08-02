package com.matuszak.projects.task;

import com.matuszak.projects.project.Project;
import com.matuszak.projects.project.ProjectPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskPersistenceImpl implements TaskPersistence {

    private final TaskRepository taskRepository;
    private final ProjectPersistence projectPersistence;

    @Autowired
    public TaskPersistenceImpl(TaskRepository taskRepository, ProjectPersistence projectPersistence) {
        this.taskRepository = taskRepository;
        this.projectPersistence = projectPersistence;
    }

    @Override
    public Task create(String projectUUID, Task task) {
        Project projectByUUID = projectPersistence.getProjectByUUID(projectUUID);
        List<Task> tasks = projectByUUID.getTasks();

        if (tasks != null) {
            tasks.add(task);
        }else{
            List<Task> newTasks = new ArrayList<>();
            newTasks.add(task);
            projectByUUID.setTasks(newTasks);
        }

        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.getTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task does not exists"));
    }

    @Override
    public List<Task> getTasksFromProject(String uuid) {
        return projectPersistence.getProjectByUUID(uuid).getTasks();
    }

    @Override
    public void delete(Task task) {
        taskRepository.delete(task);
    }
}