package com.matuszak.engineer.domain.project.service;

import com.matuszak.engineer.domain.project.exceptions.ParticipantNotFoundException;
import com.matuszak.engineer.domain.project.model.ParticipantId;
import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.ProjectProperties;
import com.matuszak.engineer.domain.project.model.dto.ProjectDTO;
import com.matuszak.engineer.domain.project.model.entity.Participant;
import com.matuszak.engineer.domain.project.model.entity.Project;
import com.matuszak.engineer.domain.project.repository.ParticipantRepository;
import com.matuszak.engineer.domain.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final ParticipantRepository participantRepository;
    private final ProjectFactory projectFactory;

    public Optional<ProjectDTO> getProjectByProjectId(ProjectId projectId) {
        return projectRepository.getProjectByProjectId(projectId)
                .map(e -> modelMapper.map(e, ProjectDTO.class));
    }

    public void addParticipant(ProjectId projectId, ParticipantId participantId){

        Optional<Project> project = this.projectRepository.getProjectByProjectId(projectId);

        Participant participant = participantRepository.getParticipantByParticipantId(participantId)
                .orElseThrow(() -> new ParticipantNotFoundException());

        project.ifPresent(e -> e.addParticipant(participant));
    }

    public Project createProject(ProjectProperties projectProperties) {
        Project project = projectFactory.createProject(projectProperties);
        return projectRepository.save(project);
    }

    public void delete(ProjectDTO projectDTO) {
        projectRepository.getProjectByProjectId(projectDTO.getProjectId())
                .ifPresent( Project::markAsDeleted );
    }

    public void close(ProjectDTO projectDTO) {
        projectRepository.getProjectByProjectId(projectDTO.getProjectId())
                .ifPresent( Project::markAsClosed );
    }
}
