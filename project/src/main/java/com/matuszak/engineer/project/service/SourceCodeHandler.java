package com.matuszak.engineer.project.service;

import com.matuszak.engineer.project.exceptions.ProjectNotFoundException;
import com.matuszak.engineer.project.model.ProjectId;
import com.matuszak.engineer.project.model.dto.SourceCodeDto;
import com.matuszak.engineer.project.model.entity.Project;
import com.matuszak.engineer.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SourceCodeHandler {

    private final ProjectLoader projectLoader;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    public void initSourceCodeRepository(ProjectId projectId, SourceCodeDto sourceCodeDto) throws ProjectNotFoundException {
        Project project = projectLoader.load(projectId);
        project.addSourceCode(sourceCodeDto);
        projectRepository.save(project);
    }

    public SourceCodeDto getRepository(ProjectId projectId) throws ProjectNotFoundException {
        Project project = projectLoader.load(projectId);
        return modelMapper.map(project.getSourceCode(), SourceCodeDto.class);
    }

}
