package com.matuszak.engineer.project.service;

import com.matuszak.engineer.project.exceptions.IssueRoomNotFoundException;
import com.matuszak.engineer.project.exceptions.ProjectNotFoundException;
import com.matuszak.engineer.project.exceptions.TaskNotFoundException;
import com.matuszak.engineer.project.exceptions.WorkerNotFoundException;
import com.matuszak.engineer.project.model.*;
import com.matuszak.engineer.project.model.dto.*;
import com.matuszak.engineer.project.model.entity.*;
import com.matuszak.engineer.project.repository.ProjectRepository;
import com.matuszak.engineer.project.repository.TaskRepository;
import com.matuszak.engineer.project.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

//import com.matuszak.engineer.domain.project.repository.TaskRepository;

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

    public WorkerDto addWorker(ProjectId projectId, String userId, ProjectRole projectRole) {

        Project project = this.projectRepository.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        Worker worker = project.hireWorker(userId, projectRole);

        this.workerRepository.save(worker);
        this.projectRepository.save(project);

        return this.modelMapper.map(worker, WorkerDto.class);
    }

    private Optional isUserInProject(ProjectId projectId, String userId) {
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
                .ifPresent(Project::finish);
    }

    public void close(ProjectId projectId) {
        projectRepository.getProjectByProjectId(projectId)
                .ifPresent(Project::close);
    }


    public Collection<ProjectDTO> getAllAvailableProjectsByUserIn(String userId) {

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

    public Collection<WorkerDto> getWorkers(ProjectId projectId) {
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

    private Collection<Worker> filterWorkersByWorkersId(Collection<Worker> workers, Collection<WorkerId> workerIds) {

        Collection<Worker> filteredWorkers = new ArrayList<>(workerIds.size());

        for (WorkerId workerId : workerIds)
            filteredWorkers.add(getWorkerByWorkerId(workers, workerId));

        return filteredWorkers;
    }

    private Worker getWorkerByWorkerId(Collection<Worker> workers, WorkerId workerIds) {
        return workers.stream()
                .filter(worker -> worker.getWorkerId().equals(workerIds.getWorkerId()))
                .findAny()
                .orElseThrow(WorkerNotFoundException::new);
    }

    private boolean isPersonInProject(ProjectId projectId, WorkerId workerId) {
        return getWorkersOfProject(projectId)
                .stream()
                .anyMatch(worker -> worker.getWorkerId().equals(workerId.getWorkerId()));
    }

    private boolean isPersonInProject(Collection<Worker> workersInProject, WorkerId workerId) {
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

    public void addWorkerToTask(ProjectId projectId, TaskId taskId, WorkerId workerId) {

        Worker worker1 = getWorkersOfProject(projectId)
                .stream()
                .filter(worker -> worker.getWorkerId().equals(workerId.getWorkerId()))
                .findAny()
                .orElseThrow(WorkerNotFoundException::new);

        this.projectRepository.getProjectByProjectId(projectId)
                .stream()
                .map(Project::getTasks)
                .flatMap(Collection::stream)
                .filter(task -> task.getTaskId().getTaskId().equals(taskId.getTaskId()))
                .findAny()
                .ifPresent(task -> task.addWorker(new WorkerId(worker1.getWorkerId())));
    }

    public void removeTask(ProjectId projectId, TaskId taskId) {
        Project project = this.projectRepository.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        project.removeTask(taskId);

        this.projectRepository.save(project);
    }

    public TaskDto getTask(ProjectId projectId, TaskId taskId) {

        Project project = this.projectRepository.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        Task task = project.getTasks().stream()
                .peek(e -> log.info(e.toString()))
                .filter(e -> e.getTaskId().getTaskId().equals(taskId.getTaskId()))
                .findAny()
                .orElseThrow(TaskNotFoundException::new);

        return this.modelMapper.map(task, TaskDto.class);
    }

    public void completeTask(ProjectId projectId, TaskId taskId) throws ProjectNotFoundException, TaskNotFoundException {

        Project project = this.projectRepository.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        project.completeTask(taskId.getTaskId());

        this.projectRepository.save(project);
    }

    public void updateTask(ProjectId projectId, TaskDto taskDto) {
        Project project = this.projectRepository.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        project.updateTask(taskDto);

        projectRepository.save(project);
    }

    public Map dashboardProject(ProjectId projectId) {

        Project project = this.projectRepository.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        return project.dashboard();
    }

    public IssueRoomDto createIssueRoom(ProjectId projectId, IssueRoomProperties issueRoomProperties) throws ProjectNotFoundException {

        Project project = this.projectRepository.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        IssueRoom issueRoom = project.createIssueRoom(issueRoomProperties);

        log.info(issueRoom.toString());

        this.projectRepository.save(project);

        log.info(issueRoom.toString());

        IssueRoomDto map = this.modelMapper.map(issueRoom, IssueRoomDto.class);

        log.info(map.toString());

        return map;
    }

    public Collection<IssueRoomDto> getIssueRooms(ProjectId projectId) throws ProjectNotFoundException, IssueRoomNotFoundException {

        Project project = this.projectRepository.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        List<IssueRoomDto> issueRooms = project.getIssueRooms()
                .stream()
                .map(e -> modelMapper.map(e, IssueRoomDto.class))
                .collect(Collectors.toList());

        return issueRooms;
    }

    public IssueRoomDto getIssueRoom(ProjectId projectId, IssueRoomId issueRoomId) throws ProjectNotFoundException, IssueRoomNotFoundException {

        Project project = this.projectRepository.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        IssueRoom issueRoom = project.getIssueRoom(issueRoomId);

        return modelMapper.map(issueRoom, IssueRoomDto.class);

    }

    public void closeIssueRoom(ProjectId projectId, IssueRoomId issueRoomId) throws ProjectNotFoundException, IssueRoomNotFoundException {

        Project project = this.projectRepository.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new);
        project.closeIssuesRoom(issueRoomId);

        projectRepository.save(project);
    }

    public void addAnswerIssueRoom(ProjectId projectId, IssueRoomId issueRoomId, AnswerDto answerDto) throws ProjectNotFoundException, IssueRoomNotFoundException {

        Project project = this.projectRepository.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        project.getIssueRoom(issueRoomId).addAnswer(new Answer(answerDto.getAuthor(), answerDto.getMessage()));

        projectRepository.save(project);
    }

    public void initSourceCodeRepository(ProjectId projectId, SourceCodeDto sourceCodeDto) throws ProjectNotFoundException{
        Project project = this.projectRepository.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new);
        project.addSourceCode(sourceCodeDto);
        projectRepository.save(project);
    }

    public SourceCodeDto getRepository(ProjectId projectId) throws ProjectNotFoundException {
        Project project = this.projectRepository.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new);
        return modelMapper.map(project.getSourceCode(), SourceCodeDto.class);
    }
}























