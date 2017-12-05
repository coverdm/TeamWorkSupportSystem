package com.matuszak.engineer.domain.project.service;

import com.matuszak.engineer.domain.project.exceptions.NoTaskFoundException;
import com.matuszak.engineer.domain.project.exceptions.ProjectNotFoundException;
import com.matuszak.engineer.domain.project.exceptions.TaskNotFoundException;
import com.matuszak.engineer.domain.project.model.ParticipantId;
import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.TaskId;
import com.matuszak.engineer.domain.project.model.TaskProperties;
import com.matuszak.engineer.domain.project.model.dto.TaskDTO;
import com.matuszak.engineer.domain.project.model.entity.Participant;
import com.matuszak.engineer.domain.project.model.entity.Project;
import com.matuszak.engineer.domain.project.model.entity.Task;
import com.matuszak.engineer.domain.project.repository.ParticipantRepository;
import com.matuszak.engineer.domain.project.repository.ProjectRepository;
import com.matuszak.engineer.domain.project.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final ParticipantRepository participantRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final TaskFactory taskFactory;
    private final TaskRepository taskRepository;

    public List<Task> getParticipantsTask(ProjectId projectId, ParticipantId participantId) {

        Collection<Participant> participants = participantRepository.getParticipantByParticipantId(participantId);

        return projectRepository.getProjectByProjectId(projectId)
                .filter(e -> e.getParticipants().contains(participants))
                .map(e -> e.getTasks()
                        .stream()
                        .filter(t -> t.getExecutors().contains(participantId))
                        .collect(Collectors.toList())
                ).orElseThrow(NoTaskFoundException::new);
    }

    public List<TaskDTO> getAllAvailableParticipantTasks(ProjectId projectId, ParticipantId participantId){

        return getParticipantsTask(projectId, participantId).stream()
                .map(e -> modelMapper.map(e, TaskDTO.class))
                .collect(Collectors.toList());

    }

    public TaskDTO createTask(ProjectId projectId, TaskProperties taskProperties) {

        Project project = projectRepository.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        Task task = taskRepository.save(taskFactory.createTask(taskProperties));

        project.addTask(task);

        return modelMapper.map(task, TaskDTO.class);
    }

    public TaskDTO finishTask(ProjectId projectId, TaskId taskId){

        Collection<Task> tasks = projectRepository.getProjectByProjectId(projectId)
                .map(Project::getTasks).orElseThrow(TaskNotFoundException::new);

        Optional<Task> task = tasks.stream()
                .filter(e -> e.getTaskId().getId().equals(taskId.getId()))
                .findFirst();

        task.ifPresent(Task::markAsCanceled);

        return modelMapper.map(task, TaskDTO.class);
    }
}
