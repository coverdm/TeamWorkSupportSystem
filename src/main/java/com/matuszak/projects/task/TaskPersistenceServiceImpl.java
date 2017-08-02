package com.matuszak.projects.task;

import com.matuszak.projects.project.Project;
import com.matuszak.projects.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskPersistenceServiceImpl implements TaskPersistenceService {

    private final TaskRepository taskRepository;
    private final ProjectService projectService;

    @Autowired
    public TaskPersistenceServiceImpl(TaskRepository taskRepository, ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;
    }

    @Override
    public Task create(String projectUUID, Task task) {
        Project projectByUUID = projectService.getProjectByUUID(projectUUID);
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
        return projectService.getProjectByUUID(uuid).getTasks();
    }

    @Override
    public void delete(Task task) {
        taskRepository.delete(task);
    }
}