package com.matuszak.engineer.project.service;

import com.matuszak.engineer.project.exceptions.ProjectNotFoundException;
import com.matuszak.engineer.project.exceptions.TaskNotFoundException;
import com.matuszak.engineer.project.interncomm.NotificationBus;
import com.matuszak.engineer.project.model.ProjectId;
import com.matuszak.engineer.project.model.TaskId;
import com.matuszak.engineer.project.model.dto.Event;
import com.matuszak.engineer.project.model.dto.NotificationDto;
import com.matuszak.engineer.project.model.dto.TaskDto;
import com.matuszak.engineer.project.model.entity.Project;
import com.matuszak.engineer.project.model.entity.Task;
import com.matuszak.engineer.project.model.entity.Worker;
import com.matuszak.engineer.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskHandler {

    private final ProjectLoader projectLoader;
    private final ModelMapper modelMapper;
    private final ProjectRepository projectRepository;
    private final NotificationBus notificationBus;

    public TaskDto createTask(ProjectId projectId, TaskDto taskDto) throws ProjectNotFoundException {

        Project project = projectLoader.load(projectId);

        Task task = project.createTask(taskDto);

        this.projectRepository.save(project);

        return this.modelMapper.map(task, TaskDto.class);
    }

    public Collection<TaskDto> getTasks(ProjectId projectId) throws TaskNotFoundException {
        return projectLoader.load(projectId)
                .getTasks()
                .stream()
                .map(e -> modelMapper.map(e, TaskDto.class))
                .collect(Collectors.toList());
    }

    public void removeTask(ProjectId projectId, TaskId taskId) {
        Project project = projectLoader.load(projectId);
        project.removeTask(taskId);
        this.projectRepository.save(project);
    }

    public TaskDto getTask(ProjectId projectId, TaskId taskId) {
        Project project = projectLoader.load(projectId);
        return this.modelMapper.map(project.getTask(taskId), TaskDto.class);
    }

    public void completeTask(ProjectId projectId, TaskId taskId) throws ProjectNotFoundException, TaskNotFoundException {
        Project project = projectLoader.load(projectId);
        project.completeTask(taskId.getTaskId());

        notificationBus.notify(
                new Event(
                        new NotificationDto("Task " + taskId.getTaskId() + " completed", "app/project/" + project.getProjectId().getUuid() + "/assignment/" + taskId.getTaskId(), "TASK_COMPLETED"),
                        project.getWorkers().stream().map(Worker::getWorkerId).collect(Collectors.toList())
                )
        );

        this.projectRepository.save(project);
    }

    public void updateTask(ProjectId projectId, TaskDto taskDto) {
        Project project = projectLoader.load(projectId);
        project.updateTask(taskDto);
        projectRepository.save(project);
    }
}
