package com.matuszak.engineer.boundary.web;

import com.matuszak.engineer.domain.project.exceptions.ProjectNotFoundException;
import com.matuszak.engineer.domain.project.model.ParticipantId;
import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.ProjectProperties;
import com.matuszak.engineer.domain.project.model.dto.ProjectDTO;
import com.matuszak.engineer.domain.project.model.dto.ProjectItemDTO;
import com.matuszak.engineer.domain.project.model.entity.Project;
import com.matuszak.engineer.domain.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Log
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;
    private final String HOST = "http://localhost:8080";

    @PostMapping("/create")
    public ResponseEntity<ProjectItemDTO> createProject(@RequestBody ProjectProperties projectProperties,
                                                        HttpServletRequest httpServletRequest,
                                                        @RequestParam  String userId){

        Boolean isUserExists = isUserRegistered(userId, httpServletRequest);

        if(isUserExists){
            Project project = projectService.createProject(projectProperties, new ParticipantId(userId));
            ProjectItemDTO projectItemDTO = modelMapper.map(project, ProjectItemDTO.class);
            return new ResponseEntity<>(projectItemDTO,HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable String projectId){
        return new ResponseEntity<>(projectService.getProjectByProjectId(new ProjectId(projectId))
                .orElseThrow(ProjectNotFoundException::new), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getListOfProjects")
    public ResponseEntity<Collection<ProjectItemDTO>> getAllProjects(@RequestParam String userId){
        Collection<ProjectItemDTO> allAvailableProjectsByUserIn =
                projectService.getAllAvailableProjectsByUserIn(new ParticipantId(userId));
        return new ResponseEntity(allAvailableProjectsByUserIn, HttpStatus.ACCEPTED);
    }

    @PostMapping("/{uuid}/addParticipant")
    public ResponseEntity addParticipant(@PathVariable String uuid,
                                         @RequestParam String userId,
                                         HttpServletRequest httpServletRequest){

        Boolean isUserExists = isUserRegistered(userId, httpServletRequest);

        if(isUserExists){
            projectService.addParticipant(new ProjectId(uuid), new ParticipantId(userId));
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    private Boolean isUserRegistered(@RequestParam String userId, HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", httpServletRequest.getHeader("Authorization"));

        HttpEntity httpEntity = new HttpEntity("parameters", headers);
        String url = HOST + "/api/auth/check?subjectId=" + userId;

        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, Boolean.class).getBody();
    }
}
