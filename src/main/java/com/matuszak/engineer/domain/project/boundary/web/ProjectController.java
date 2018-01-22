package com.matuszak.engineer.domain.project.boundary.web;

import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.ProjectProperties;
import com.matuszak.engineer.domain.project.model.ProjectRole;
import com.matuszak.engineer.domain.project.model.WorkerId;
import com.matuszak.engineer.domain.project.model.dto.WorkerDto;
import com.matuszak.engineer.domain.project.model.dto.SourceCodeDto;
import com.matuszak.engineer.domain.project.model.entity.Project;
import com.matuszak.engineer.domain.project.exceptions.ProjectNotFoundException;
import com.matuszak.engineer.domain.project.model.dto.ProjectDTO;
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
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectProperties projectProperties,
                                                    HttpServletRequest httpServletRequest,
                                                    @RequestParam  String userId){

        log.info("ProjectProperties: " + projectProperties.toString());

        Boolean isUserExists = isUserRegistered(userId, httpServletRequest);
        log.info("isUserExists: " + isUserExists.toString());

        if(isUserExists){
            Project project = projectService.createProject(projectProperties, userId);
            log.info("Created project: " + project.toString());
            ProjectDTO projectDTO = modelMapper.map(project, ProjectDTO.class);
            log.info("Mapped to projectDTO");
            return new ResponseEntity<>(projectDTO,HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProjectDTO> getProject(@RequestBody String uuid){

        log.info("ProjectId: " + uuid);

        return new ResponseEntity<>(projectService.getProjectByProjectId(new ProjectId(uuid))
                .orElseThrow(ProjectNotFoundException::new), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Collection<ProjectDTO>> getAllProjects(@RequestParam String userId){

        log.info("ProjectId: " + userId);

        Collection<ProjectDTO> allAvailableProjectsByUserIn =
                projectService.getAllAvailableProjectsByUserIn(userId);
        return new ResponseEntity(allAvailableProjectsByUserIn, HttpStatus.ACCEPTED);
    }


    //TODO need to consume projectRole
    @PostMapping("/{uuid}")
    public ResponseEntity addWorker(@PathVariable String uuid,
                                    @RequestParam String userId,
                                    HttpServletRequest httpServletRequest){

        log.info("ProjectId: " + userId);

        Boolean isUserExists = isUserRegistered(userId, httpServletRequest);

        if(isUserExists){
            projectService.addWorker(new ProjectId(uuid), userId, ProjectRole.SENIOR_JAVA_DEVELOPER);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{uuid}/workers")
    public ResponseEntity<Collection<WorkerDto>> getParticipants(@PathVariable String uuid){
        Collection<WorkerDto> workers = this.projectService.getWorkers(new ProjectId(uuid));
        return new ResponseEntity<>(workers, HttpStatus.OK);

    }

    //TODO the endpoint needs to be tested
    @PostMapping("/{uuid}/repository")
    public ResponseEntity createNewRepositoryHolder(@PathVariable String uuid,
                                                    @RequestBody SourceCodeDto sourceCodeDto){

        this.projectService.addRepositoryLink(new ProjectId(uuid), sourceCodeDto);

        return new ResponseEntity(HttpStatus.OK);
    }

    private Boolean isUserRegistered(@RequestParam String userEmail, HttpServletRequest httpServletRequest) {

        log.info("UserId: " + userEmail);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", httpServletRequest.getHeader("Authorization"));

        HttpEntity httpEntity = new HttpEntity("parameters", headers);
        String url = HOST + "/api/auth/check?subjectId=" + userEmail;

        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, Boolean.class).getBody();
    }
}