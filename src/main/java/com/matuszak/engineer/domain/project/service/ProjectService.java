package com.matuszak.engineer.domain.project.service;

import com.matuszak.engineer.domain.project.entity.Participant;
import com.matuszak.engineer.domain.project.entity.Project;
import com.matuszak.engineer.domain.project.model.dto.ParticipantDTO;
import com.matuszak.engineer.domain.project.model.dto.ProjectDTO;
import com.matuszak.engineer.domain.project.repository.ParticipantRepository;
import com.matuszak.engineer.domain.project.repository.ProjectRepository;
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
    private final ParticipantRepository participantRepository;

    public Optional<ProjectDTO> getProjectByUUID(String uuid) {
        return projectRepository.getProjectByUuid(uuid)
                .map(e -> modelMapper.map(e, ProjectDTO.class));
    }

    public Project save(ProjectDTO projectDTO) {

        Collection<ParticipantDTO> participants = projectDTO.getParticipants();
        List<Participant> participantCollection = new ArrayList<>();

        for(ParticipantDTO participantDTO:participants){
            participantCollection.add(participantRepository.getOne(participantDTO.getId()));
        }

        Project build = new Project(UUID.randomUUID().toString(), projectDTO.getName());

        return projectRepository.save(build);
    }

    //TODO improve update method
    public void update(ProjectDTO projectDTO) {
        projectRepository.getProjectByUuid(projectDTO.getUuid()).ifPresent(e -> updateProperties(e, projectDTO));
    }

    public void delete(ProjectDTO projectDTO) {
        projectRepository.getProjectByUuid(projectDTO.getUuid()).ifPresent(projectRepository::delete);
    }

    public List<ProjectDTO> getProjectsByParticipant(ParticipantDTO participantDTO) {

        Participant participant = participantRepository.getOne(participantDTO.getId());

        return projectRepository.getProjectsByParticipants(Arrays.asList(participant))
                .peek(e -> log.info("Before mapping: " + e))
                .map(e -> modelMapper.map(e, ProjectDTO.class))
                .peek(e -> log.info("After mapping: " + e))
                .collect(Collectors.toList());
    }

    private Project updateProperties(Project project, ProjectDTO projectDTO){
        project.setName(projectDTO.getName());
        return project;
    }

}
