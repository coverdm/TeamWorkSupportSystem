package com.matuszak.engineer.domain.project.service;

import com.matuszak.engineer.domain.project.model.ParticipantLevel;
import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.ProjectProperties;
import com.matuszak.engineer.domain.project.model.dto.ProjectDTO;
import com.matuszak.engineer.domain.project.model.dto.SourceCodeDto;
import com.matuszak.engineer.domain.project.model.entity.Participant;
import com.matuszak.engineer.domain.project.model.entity.Project;
import com.matuszak.engineer.domain.project.model.entity.SourceCode;
import com.matuszak.engineer.domain.project.repository.ParticipantRepository;
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
    private final ParticipantRepository participantRepository;
    private final ProjectFactory projectFactory;
    private final SourceCodeLinkRepository sourceCodeLinkRepository;

    public Optional<ProjectDTO> getProjectByProjectId(ProjectId projectId) {
        return projectRepository.getProjectByProjectId(projectId)
                .map(e -> modelMapper.map(e, ProjectDTO.class));
    }

    public void addParticipant(ProjectId projectId, String userEmail){
        this.projectRepository.getProjectByProjectId(projectId)
                .ifPresent(e -> {
                    Participant participant = new Participant(new UserId(userEmail), ParticipantLevel.PROGRAMMER);
                    this.participantRepository.save(participant);
                    e.addParticipant(participant);
                });
    }

    public Project createProject(ProjectProperties projectProperties, String userEmail) {
        Project project = projectFactory.createProject(projectProperties);
        Participant participant = participantRepository.save(new Participant(new UserId(userEmail), ParticipantLevel.OWNER));
        project.addParticipant(participant);
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


    public Collection<ProjectDTO> getAllAvailableProjectsByUserIn(String userEmail){

        return (List<ProjectDTO>) getProjectsByUserIn(userEmail)
                .stream()
                .map(e -> modelMapper.map(e, ProjectDTO.class))
                .collect(Collectors.toList());
    }

    private Collection getProjectsByUserIn(String userEmail) {

        Collection<Participant> participants =
                participantRepository.getParticipantByUserId(new UserId(userEmail));

        Collection<Project> projects =
                projectRepository.findProjectsByParticipantsIn(participants);

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
}























