package com.matuszak.projects.task.service;

import com.matuszak.projects.project.entity.Project;
import com.matuszak.projects.project.service.ProjectPersistence;
import com.matuszak.projects.task.exceptions.TaskNotFoundException;
import com.matuszak.projects.task.repository.TaskRepository;
import com.matuszak.projects.task.entity.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log
@RequiredArgsConstructor
public class TaskPersistenceImpl implements TaskPersistence {

    private final TaskRepository taskRepository;
    private final ProjectPersistence projectPersistence;

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
    public Optional<Task> getTaskById(Long id) {
        return Optional.ofNullable(taskRepository.findOne(id));
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