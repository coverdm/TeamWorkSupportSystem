package com.matuszak.engineer.project.service;

import com.matuszak.engineer.project.exceptions.IssueRoomNotFoundException;
import com.matuszak.engineer.project.exceptions.ProjectNotFoundException;
import com.matuszak.engineer.project.interncomm.NotificationBus;
import com.matuszak.engineer.project.model.IssueRoomId;
import com.matuszak.engineer.project.model.ProjectId;
import com.matuszak.engineer.project.model.dto.*;
import com.matuszak.engineer.project.model.entity.Answer;
import com.matuszak.engineer.project.model.entity.IssueRoom;
import com.matuszak.engineer.project.model.entity.Project;
import com.matuszak.engineer.project.model.entity.Worker;
import com.matuszak.engineer.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueRoomHandler {

    private final ProjectLoader projectLoader;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final NotificationBus notificationBus;

    public IssueRoomDto createIssueRoom(ProjectId projectId, IssueRoomProperties issueRoomProperties) throws ProjectNotFoundException {

        Project project = projectLoader.load(projectId);

        IssueRoom issueRoom = project.createIssueRoom(issueRoomProperties);

        this.projectRepository.save(project);

        notificationBus.notify(
                new Event(
                        new NotificationDto("Created new issue room", "app/project/" + project.getProjectId().getUuid() + "/issue-room/" + issueRoom.getIssueRoomId(), "ISSUE_ROOM_CREATED"),
                        project.getWorkers().stream().map(Worker::getWorkerId).collect(Collectors.toList())
                )
        );

        IssueRoomDto map = this.modelMapper.map(issueRoom, IssueRoomDto.class);

        return map;
    }

    public Collection<IssueRoomDto> getIssueRooms(ProjectId projectId) throws ProjectNotFoundException, IssueRoomNotFoundException {

        Project project = projectLoader.load(projectId);

        List<IssueRoomDto> issueRooms = project.getIssueRooms()
                .stream()
                .map(e -> modelMapper.map(e, IssueRoomDto.class))
                .collect(Collectors.toList());

        return issueRooms;
    }

    public IssueRoomDto getIssueRoom(ProjectId projectId, IssueRoomId issueRoomId) throws ProjectNotFoundException, IssueRoomNotFoundException {

        Project project = projectLoader.load(projectId);

        IssueRoom issueRoom = project.getIssueRoom(issueRoomId);

        return modelMapper.map(issueRoom, IssueRoomDto.class);

    }

    public void closeIssueRoom(ProjectId projectId, IssueRoomId issueRoomId) throws ProjectNotFoundException, IssueRoomNotFoundException {

        Project project = projectLoader.load(projectId);
        project.closeIssuesRoom(issueRoomId);

        notificationBus.notify(
                new Event(
                        new NotificationDto("Closed issue room", "app/project/" + project.getProjectId().getUuid() + "/issue-room/" + issueRoomId.getId(), "ISSUE_ROOM_CLOSED"),
                        project.getWorkers().stream().map(Worker::getWorkerId).collect(Collectors.toList())
                )
        );

        projectRepository.save(project);
    }

    public void addAnswerIssueRoom(ProjectId projectId, IssueRoomId issueRoomId, AnswerDto answerDto) throws ProjectNotFoundException, IssueRoomNotFoundException {

        Project project = projectLoader.load(projectId);
        project.getIssueRoom(issueRoomId).addAnswer(new Answer(answerDto.getAuthor(), answerDto.getMessage()));

        notificationBus.notify(
                new Event(
                        new NotificationDto(answerDto.getAuthor() + " answers to your question", "app/project/" + project.getProjectId().getUuid() + "/issue-room/" + issueRoomId.getId(), "ISSUE_ROOM_ANSWER"),
                        Arrays.asList(project.getIssueRoom(issueRoomId).getQuestion().getAuthor())
                )
        );

        projectRepository.save(project);
    }
}
