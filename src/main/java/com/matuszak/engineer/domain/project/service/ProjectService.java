package com.matuszak.engineer.domain.project.service;

import com.matuszak.engineer.domain.project.exceptions.ProjectNotFoundException;
import com.matuszak.engineer.domain.project.exceptions.TaskNotFoundException;
import com.matuszak.engineer.domain.project.exceptions.WorkerNotFoundException;
import com.matuszak.engineer.domain.project.model.*;
import com.matuszak.engineer.domain.project.model.dto.ProjectDTO;
import com.matuszak.engineer.domain.project.model.dto.TaskDto;
import com.matuszak.engineer.domain.project.model.dto.WorkerDto;
import com.matuszak.engineer.domain.project.model.entity.Project;
import com.matuszak.engineer.domain.project.model.entity.Task;
import com.matuszak.engineer.domain.project.model.entity.Worker;
//import com.matuszak.engineer.domain.project.repository.TaskRepository;
import com.matuszak.engineer.domain.project.repository.ProjectRepository;
import com.matuszak.engineer.domain.project.repository.TaskRepository;
import com.matuszak.engineer.domain.project.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final TaskRepository taskRepository;

    public Optional<ProjectDTO> getProjectByProjectId(ProjectId projectId) {
        return projectRepository.getProjectByProjectId(projectId)
                .map(e -> modelMapper.map(e, ProjectDTO.class));
    }

    public WorkerDto addWorker(ProjectId projectId, String userId, ProjectRole projectRole){
//        this.projectRepository.getProjectByProjectId(projectId)
//                .ifPresent(e -> {
//
//                    isUserInProject(projectId, userId)
//                            .ifPresent(workerId -> { throw new WorkerAlreadyHiredException("Worker " + workerId + " is already hired"); });
//
//                    Worker worker = new Worker(new WorkerId(userId), projectRole);
//                    this.workerRepository.save(worker);
//                    e.addWorker(worker);
//                    this.projectRepository.save(e);
//                });

        Project project = this.projectRepository.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        Worker worker = project.hireWorker(userId, projectRole);

        this.workerRepository.save(worker);
        this.projectRepository.save(project);

        return this.modelMapper.map(worker, WorkerDto.class);
    }

    private Optional isUserInProject(ProjectId projectId, String userId){
        return getWorkersOfProject(projectId)
                .stream()
                .map(Worker::getWorkerId)
                .filter(workerId -> workerId.equals(userId))
                .findAny();
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

    public void delete(ProjectId projectId) {
        projectRepository.getProjectByProjectId(projectId)
                .ifPresent( Project::finish);
    }

    public void close(ProjectId projectId) {
        projectRepository.getProjectByProjectId(projectId)
                .ifPresent( Project::close);
    }


    public Collection<ProjectDTO> getAllAvailableProjectsByUserIn(String userId){

        return (List<ProjectDTO>) getProjectsByUserIn(userId)
                .stream()
                .map(e -> modelMapper.map(e, ProjectDTO.class))
                .collect(Collectors.toList());
    }

    private Collection getProjectsByUserIn(String userId) {

        Collection<Worker> workers = workerRepository.getWorkerByWorkerId(new WorkerId(userId));

        Collection<Project> projects =
                projectRepository.findProjectsByWorkersIn(workers);

        return projects;
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

    //TODO ITS NOT WORKING
    public Collection<TaskDto> getTasks(ProjectId projectId) throws TaskNotFoundException {
        return this.projectRepository.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new)
                .getTasks()
                .stream()
                .map(e -> modelMapper.map(e, TaskDto.class))
                .collect(Collectors.toList());
    }

    public TaskDto createTask(ProjectId projectId, TaskDto taskDto) throws ProjectNotFoundException {

        Project project = this.projectRepository.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        Task task = project.createTask(taskDto);

        this.taskRepository.save(task);

        this.projectRepository.save(project);

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

        first.addWorkers(confirmedWorkers.stream().map(Worker::getWorkerId).map(WorkerId::new).collect(Collectors.toList()));

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
                .ifPresent(task -> task.addWorker(new WorkerId(worker1.getWorkerId())));
    }

    public void removeTask(ProjectId projectId, TaskId taskId){

        this.projectRepository.getProjectByProjectId(projectId)
                .ifPresent(e -> e.removeTask(taskId));
    }

    public TaskDto getTask(ProjectId projectId, TaskId taskId){

        Collection<Task> tasks = this.projectRepository.getProjectByProjectId(projectId)
                .map(Project::getTasks)
                .get();
        log.info("Jestem az tu hehe");

        log.info(taskId.getTaskId().toString());
        log.info(tasks.toString());

        Task task = tasks.stream()
                .peek(e -> log.info(e.toString()))
                .filter(e -> e.getTaskId().getTaskId().equals(taskId.getTaskId()))
                .findAny()
                .orElseThrow(TaskNotFoundException::new);

        return this.modelMapper.map(task, TaskDto.class);
    }
}























