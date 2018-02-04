package com.matuszak.engineer.project;

import com.matuszak.engineer.project.model.ProjectId;
import com.matuszak.engineer.project.model.ProjectProperties;
import com.matuszak.engineer.project.model.entity.Project;
import com.matuszak.engineer.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DumpValues implements ApplicationListener<ContextRefreshedEvent> {

    private final ProjectRepository projectRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        projectRepository.save(new Project(new ProjectId("asdasd"), new ProjectProperties("nameassadasd", "descasdasd")));
    }

}
