package com.matuszak.engineer.project.service;

import com.matuszak.engineer.project.exceptions.ProjectNotFoundException;
import com.matuszak.engineer.project.interncomm.NotificationBus;
import com.matuszak.engineer.project.model.ProjectId;
import com.matuszak.engineer.project.model.ProjectProperties;
import com.matuszak.engineer.project.model.ProjectRole;
import com.matuszak.engineer.project.model.WorkerId;
import com.matuszak.engineer.project.model.dto.Event;
import com.matuszak.engineer.project.model.dto.NotificationDto;
import com.matuszak.engineer.project.model.dto.ProjectDTO;
import com.matuszak.engineer.project.model.dto.WorkerDto;
import com.matuszak.engineer.project.model.entity.Project;
import com.matuszak.engineer.project.model.entity.Worker;
import com.matuszak.engineer.project.repository.ProjectRepository;
import com.matuszak.engineer.project.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Log
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final WorkerRepository workerRepository;
    private final ProjectFactory projectFactory;
    private final ProjectLoader projectLoader;
    private final NotificationBus notificationBus;

    public ProjectDTO loadProject(ProjectId projectId) throws ProjectNotFoundException{
        return modelMapper.map(projectLoader.load(projectId), ProjectDTO.class);
    }

    public WorkerDto addWorker(ProjectId projectId, String userId, ProjectRole projectRole) throws ProjectNotFoundException{

        Project project = projectLoader.load(projectId);

        Worker worker = project.hireWorker(userId, projectRole);

        this.projectRepository.save(project);

        notificationBus.notify(
                new Event(
                        new NotificationDto("You've been invited to project", "http://localhost:4200/app/project/" + projectId.getUuid(), "INVITATION"),
                        Arrays.asList(userId)
                )
        );

        return this.modelMapper.map(worker, WorkerDto.class);
    }

    public Project createProject(ProjectProperties projectProperties, String userEmail) {

        Project project = projectFactory.createProject(projectProperties);

        Worker owner = Worker.builder()
                .projectRole(ProjectRole.OWNER)
                .workerId(new WorkerId(userEmail))
                .build();

        Worker worker = workerRepository.save(owner);

        project.addWorker(worker);
        return projectRepository.save(project);
    }

    public Collection<ProjectDTO> getAllAvailableProjectsByUserIn(String userId){
        return projectRepository.findAll()
                .stream()
                .filter(project -> project.isHiredInProject(userId))
                .map(project -> modelMapper.map(project, ProjectDTO.class))
                .collect(Collectors.toList());
    }

    public Collection<WorkerDto> getWorkers(ProjectId projectId) {
        return projectLoader.load(projectId)
                .getWorkers()
                .stream()
                .map(e -> modelMapper.map(e, WorkerDto.class))
                .collect(Collectors.toList());
    }

    public Map dashboardProject(ProjectId projectId) {
        return projectLoader.load(projectId).dashboard();
    }

}























