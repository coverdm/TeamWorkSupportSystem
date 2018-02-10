package com.matuszak.engineer.project.service;

import com.matuszak.engineer.project.exceptions.ProjectNotFoundException;
import com.matuszak.engineer.project.model.ProjectId;
import com.matuszak.engineer.project.model.entity.Project;
import com.matuszak.engineer.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectLoader {

    private final ProjectRepository projectRepository;

    public Project load(ProjectId projectId) throws ProjectNotFoundException {
        return projectRepository.getProjectByProjectId(projectId)
                .orElseThrow(ProjectNotFoundException::new);
    }
}
