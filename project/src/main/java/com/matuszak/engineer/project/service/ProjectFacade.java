package com.matuszak.engineer.project.service;

import com.matuszak.engineer.project.exceptions.IssueRoomNotFoundException;
import com.matuszak.engineer.project.exceptions.ProjectNotFoundException;
import com.matuszak.engineer.project.exceptions.TaskNotFoundException;
import com.matuszak.engineer.project.model.*;
import com.matuszak.engineer.project.model.dto.*;
import com.matuszak.engineer.project.model.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProjectFacade {

    private final ProjectService projectService;
    private final IssueRoomHandler issueRoomHandler;
    private final TaskHandler taskHandler;
    private final SourceCodeHandler sourceCodeHandler;

    public ProjectDTO loadProject(ProjectId projectId){
        return projectService.loadProject(projectId);
    }

    public Project createProject(ProjectProperties projectProperties, String userEmail) {
        return projectService.createProject(projectProperties, userEmail);
    }

    public Collection<ProjectDTO> getProjects(String userId){
        return projectService.getAllAvailableProjectsByUserIn(userId);
    }

    public Collection<WorkerDto> getWorkers(ProjectId projectId){
        return projectService.getWorkers(projectId);
    }

    public WorkerDto addWorker(ProjectId projectId, String userId, ProjectRole projectRole) throws ProjectNotFoundException{
        return projectService.addWorker(projectId, userId, projectRole);
    }

    public Map dashboard(ProjectId projectId){
        return projectService.dashboardProject(projectId);
    }

    public IssueRoomDto createIssueRoom(ProjectId projectId, IssueRoomProperties issueRoomProperties) throws ProjectNotFoundException {
        return issueRoomHandler.createIssueRoom(projectId, issueRoomProperties);
    }

    public Collection<IssueRoomDto> getIssueRooms(ProjectId projectId) throws ProjectNotFoundException, IssueRoomNotFoundException {
        return issueRoomHandler.getIssueRooms(projectId);
    }

    public IssueRoomDto getIssueRoom(ProjectId projectId, IssueRoomId issueRoomId) throws ProjectNotFoundException, IssueRoomNotFoundException {
        return issueRoomHandler.getIssueRoom(projectId, issueRoomId);
    }

    public void closeIssueRoom(ProjectId projectId, IssueRoomId issueRoomId) throws ProjectNotFoundException, IssueRoomNotFoundException {
        issueRoomHandler.closeIssueRoom(projectId, issueRoomId);
    }

    public void addAnswerIssueRoom(ProjectId projectId, IssueRoomId issueRoomId, AnswerDto answerDto) throws ProjectNotFoundException, IssueRoomNotFoundException {
        issueRoomHandler.addAnswerIssueRoom(projectId, issueRoomId, answerDto);
    }

    public TaskDto createTask(ProjectId projectId, TaskDto taskDto) throws ProjectNotFoundException {
        return taskHandler.createTask(projectId, taskDto);
    }

    public Collection<TaskDto> getTasks(ProjectId projectId) throws TaskNotFoundException {
        return taskHandler.getTasks(projectId);
    }

    public void removeTask(ProjectId projectId, TaskId taskId) {
        taskHandler.removeTask(projectId, taskId);
    }

    public TaskDto getTask(ProjectId projectId, TaskId taskId) {
        return taskHandler.getTask(projectId, taskId);
    }

    public void completeTask(ProjectId projectId, TaskId taskId) throws ProjectNotFoundException, TaskNotFoundException {
        taskHandler.completeTask(projectId, taskId);
    }

    public void updateTask(ProjectId projectId, TaskDto taskDto) {
        taskHandler.updateTask(projectId, taskDto);
    }

    public void initSourceCodeRepository(ProjectId projectId, SourceCodeDto sourceCodeDto) throws ProjectNotFoundException {
        sourceCodeHandler.initSourceCodeRepository(projectId, sourceCodeDto);
    }

    public SourceCodeDto getRepository(ProjectId projectId) throws ProjectNotFoundException {
        return sourceCodeHandler.getRepository(projectId);
    }
}
