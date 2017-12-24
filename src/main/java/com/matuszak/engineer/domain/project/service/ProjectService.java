package com.matuszak.engineer.domain.project.service;

import com.matuszak.engineer.domain.project.exceptions.WorkerNotFoundException;
import com.matuszak.engineer.domain.project.model.ProjectRole;
import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.ProjectProperties;
import com.matuszak.engineer.domain.project.model.dto.WorkerDto;
import com.matuszak.engineer.domain.project.model.dto.ProjectDTO;
import com.matuszak.engineer.domain.project.model.dto.SourceCodeDto;
import com.matuszak.engineer.domain.project.model.entity.Worker;
import com.matuszak.engineer.domain.project.model.entity.Project;
import com.matuszak.engineer.domain.project.model.entity.SourceCode;
import com.matuszak.engineer.domain.project.repository.WorkerRepository;
import com.matuszak.engineer.domain.project.repository.ProjectRepository;
import com.matuszak.engineer.domain.project.repository.SourceCodeLinkRepository;
import com.matuszak.engineer.infrastructure.entity.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final WorkerRepository workerRepository;
    private final ProjectFactory projectFactory;
    private final SourceCodeLinkRepository sourceCodeLinkRepository;

    public Optional<ProjectDTO> getProjectByProjectId(ProjectId projectId) {
        return projectRepository.getProjectByProjectId(projectId)
                .map(e -> modelMapper.map(e, ProjectDTO.class));
    }

    public void addWorker(ProjectId projectId, String userEmail){
        this.projectRepository.getProjectByProjectId(projectId)
                .ifPresent(e -> {
                    Worker worker = new Worker(new UserId(userEmail), ProjectRole.PROJECT_MANAGER);
                    this.workerRepository.save(worker);
                    e.addWorker(worker);
                });
    }

    public Project createProject(ProjectProperties projectProperties, String userEmail) {
        Project project = projectFactory.createProject(projectProperties);
        Worker worker = workerRepository.save(new Worker(new UserId(userEmail), ProjectRole.OWNER));
        project.addWorker(worker);
        return projectRepository.save(project);
    }

    public void delete(ProjectId projectId) {
        projectRepository.getProjectByProjectId(projectId)
                .ifPresent( Project::markAsDeleted );
    }

    public void close(ProjectId projectId) {
        projectRepository.getProjectByProjectId(projectId)
                .ifPresent( Project::markAsClosed );
    }


    public Collection<ProjectDTO> getAllAvailableProjectsByUserIn(String userId){

        return (List<ProjectDTO>) getProjectsByUserIn(userId)
                .stream()
                .map(e -> modelMapper.map(e, ProjectDTO.class))
                .collect(Collectors.toList());
    }

    private Collection getProjectsByUserIn(String userId) {

        Collection<Worker> workers =
                workerRepository.getWorkerByUserId(new UserId(userId));

        log.info(workers.toString());

        Collection<Project> projects =
                projectRepository.findProjectsByWorkersIn(workers);

        log.info(projects.toString());

        return projects;
    }

    public void addRepositoryLink(ProjectId projectId, SourceCodeDto sourceCodeDto){

        projectRepository.getProjectByProjectId(projectId)
                .ifPresent( e -> {
                    SourceCode sourceCode = new SourceCode(sourceCodeDto.getSourceCodeRepositoryHolderType(), sourceCodeDto.getSourceCodeHolderLink());
                    this.sourceCodeLinkRepository.save(sourceCode);
                    e.addSourceCode(sourceCode);
                });

    }

    public Collection<WorkerDto> getWorkers(ProjectId projectId){
        return this.getWorkersInProject(projectId)
                .stream()
                .map(e -> modelMapper.map(e, WorkerDto.class))
                .collect(Collectors.toList());
    }

    private Collection<Worker> getWorkersInProject(ProjectId uuid) {
        return this.projectRepository.getProjectByProjectId(uuid)
                .map(Project::getWorkers)
                .orElseThrow(WorkerNotFoundException::new);

    }
}























