package com.matuszak.engineer.domain.project.service;

import com.matuszak.engineer.domain.project.exceptions.ProjectNotFoundException;
import com.matuszak.engineer.domain.project.exceptions.TaskNotFoundException;
import com.matuszak.engineer.domain.project.exceptions.WorkerNotFoundException;
import com.matuszak.engineer.domain.project.model.ProjectRole;
import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.ProjectProperties;
import com.matuszak.engineer.domain.project.model.TaskId;
import com.matuszak.engineer.domain.project.model.dto.TaskDto;
import com.matuszak.engineer.domain.project.model.dto.WorkerDto;
import com.matuszak.engineer.domain.project.model.dto.ProjectDTO;
import com.matuszak.engineer.domain.project.model.dto.SourceCodeDto;
import com.matuszak.engineer.domain.project.model.entity.Task;
import com.matuszak.engineer.domain.project.model.entity.Worker;
import com.matuszak.engineer.domain.project.model.entity.Project;
import com.matuszak.engineer.domain.project.model.entity.SourceCode;
import com.matuszak.engineer.domain.project.repository.TaskRepository;
import com.matuszak.engineer.domain.project.repository.WorkerRepository;
import com.matuszak.engineer.domain.project.repository.ProjectRepository;
import com.matuszak.engineer.domain.project.repository.SourceCodeLinkRepository;
import com.matuszak.engineer.domain.project.model.WorkerId;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final WorkerRepository workerRepository;
    private final ProjectFactory projectFactory;
    private final TaskRepository taskRepository;
    private final SourceCodeLinkRepository sourceCodeLinkRepository;

    public Optional<ProjectDTO> getProjectByProjectId(ProjectId projectId) {
        return projectRepository.getProjectByProjectId(projectId)
                .map(e -> modelMapper.map(e, ProjectDTO.class));
    }

    public void addWorker(ProjectId projectId, String userId, ProjectRole projectRole){
        this.projectRepository.getProjectByProjectId(projectId)
                .ifPresent(e -> {
                    Worker worker = new Worker(new WorkerId(userId), projectRole);
                    this.workerRepository.save(worker);
                    e.addWorker(worker);
                });
    }

    public Project createProject(ProjectProperties projectProperties, String userEmail) {
        Project project = projectFactory.createProject(projectProperties);
        Worker worker = workerRepository.save(new Worker(new WorkerId(userEmail), ProjectRole.OWNER));
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

        Collection<Worker> workers = workerRepository.getWorkerByWorkerId(new WorkerId(userId));

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
        return this.getWorkersOfProject(projectId)
                .stream()
                .map(e -> modelMapper.map(e, WorkerDto.class))
                .collect(Collectors.toList());
    }

    private Collection<Worker> getWorkersOfProject(ProjectId uuid) {
        return this.projectRepository.getProjectByProjectId(uuid)
                .map(Project::getWorkers)
                .orElseThrow(WorkerNotFoundException::new);

    }

    public Collection<TaskDto> getTasks(ProjectId projectId) throws TaskNotFoundException {
        return this.projectRepository.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new)
                .getTasks()
                .stream()
                .map(e -> modelMapper.map(e, TaskDto.class))
                .collect(Collectors.toList());
    }

    public TaskDto createTask(ProjectId projectId, TaskDto taskDto) throws ProjectNotFoundException {

//        Project project = this.projectRepository.getProjectByProjectId(projectId)
//                .orElseThrow(ProjectNotFoundException::new);
//
        Task task = new Task(taskDto.getTitle(), taskDto.getDescription(), taskDto.getDifficult(), taskDto.getDeadline());
//
        Collection<Worker> workers = this.getWorkersOfProject(projectId);
//
        List<WorkerId> workersIds = taskDto.getWorkers()
                .stream()
                .map(WorkerDto::getWorkerId)
                .map(WorkerId::new)
                .filter(workerId -> isPersonInProject(workers, workerId))
                .collect(Collectors.toList());
//
//        Collection<Worker> confirmedWorkers = this.filterWorkersByWorkersId(workers, workersIds);
//
//        task.addWorkers(confirmedWorkers);
//        project.addTask(task);
//
//        this.taskRepository.save(task);
//        this.projectRepository.save(project);
//
        return this.modelMapper.map(task, TaskDto.class);
    }

    private Collection<Worker> filterWorkersByWorkersId(Collection<Worker> workers, Collection<WorkerId> workerIds){

        Collection<Worker> filteredWorkers = new ArrayList<>(workerIds.size());

        for(WorkerId workerId: workerIds)
            filteredWorkers.add(getWorkerByWorkerId(workers, workerId));

        return filteredWorkers;
    }

    private Worker getWorkerByWorkerId(Collection<Worker> workers, WorkerId workerIds){
        return workers.stream()
                .filter(worker -> worker.getWorkerId().equals(workerIds.getWorkerId()))
                .findAny()
                .orElseThrow(WorkerNotFoundException::new);
    }

    private boolean isPersonInProject(ProjectId projectId, WorkerId workerId){
        return getWorkersOfProject(projectId)
                .stream()
                .anyMatch(worker -> worker.getWorkerId().equals(workerId.getWorkerId()));
    }

    private boolean isPersonInProject(Collection<Worker> workersInProject, WorkerId workerId){
        return workersInProject
                .stream()
                .anyMatch(worker -> worker.getWorkerId().equals(workerId.getWorkerId()));
    }

    public void addWorkersToTask(ProjectId projectId, TaskId taskId, Collection<WorkerDto> workers) throws ProjectNotFoundException {

        Project project = this.projectRepository.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        Collection<Worker> workersOfProject = project.getWorkers();

        List<WorkerId> workersIds = workers
                .stream()
                .map(WorkerDto::getWorkerId)
                .map(WorkerId::new)
                .filter(workerId -> isPersonInProject(workersOfProject, workerId))
                .collect(Collectors.toList());

        Collection<Worker> confirmedWorkers = filterWorkersByWorkersId(workersOfProject, workersIds);

        Task first = project.getTasks()
                .stream()
                .filter(task -> task.getTaskId().getTaskId().equals(taskId.getTaskId()))
                .findFirst()
                .orElseThrow(TaskNotFoundException::new);

        first.addWorkers(confirmedWorkers);

        taskRepository.save(first);
        projectRepository.save(project);
    }

    public void addWorkerToTask(ProjectId projectId, TaskId taskId, WorkerId workerId){

        Worker worker1 = getWorkersOfProject(projectId)
                .stream()
                .filter(worker -> worker.getWorkerId().equals(workerId.getWorkerId()))
                .findAny()
                .orElseThrow(WorkerNotFoundException::new);

        this.projectRepository.getProjectByProjectId(projectId)
                .stream()
                .map(Project::getTasks)
                .flatMap(tasks -> tasks.stream())
                .filter(task -> task.getTaskId().getTaskId().equals(taskId.getTaskId()))
                .findAny()
                .ifPresent(task -> task.addWorker(worker1));
    }

    public void removeTask(ProjectId projectId, TaskId taskId){

        this.projectRepository.getProjectByProjectId(projectId)
                .ifPresent(e -> e.removeTask(taskId));
    }

    public void updateTask(ProjectId projectId, TaskDto taskDto) {
        this.taskRepository.getTaskByTaskId(taskDto.getTaskId())
                .ifPresent(task -> {
                    task.setTitle(taskDto.getTitle());
                    task.setDescription(taskDto.getDescription());
                    task.setDifficult(taskDto.getDifficult());
                    this.taskRepository.save(task);
                });
    }
}























