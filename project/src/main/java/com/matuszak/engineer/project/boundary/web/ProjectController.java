package com.matuszak.engineer.project.boundary.web;

import com.matuszak.engineer.project.exceptions.ProjectNotFoundException;
import com.matuszak.engineer.project.exceptions.WorkerAlreadyHiredException;
import com.matuszak.engineer.project.model.ProjectId;
import com.matuszak.engineer.project.model.ProjectProperties;
import com.matuszak.engineer.project.model.dto.HireModel;
import com.matuszak.engineer.project.model.dto.ProjectDTO;
import com.matuszak.engineer.project.model.dto.SourceCodeDto;
import com.matuszak.engineer.project.model.dto.WorkerDto;
import com.matuszak.engineer.project.model.entity.Project;
import com.matuszak.engineer.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;
import java.util.Collection;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log
public class ProjectController {

    private final ProjectService projectService;
    private final ModelMapper modelMapper;

    @GetMapping("/getAll")
    public ResponseEntity<Collection<ProjectDTO>> getAllProjects(@RequestParam String userId, HttpServletRequest httpServletRequest) {
        Collection<ProjectDTO> allAvailableProjectsByUserIn =
                projectService.getAllAvailableProjectsByUserIn(userId);
        return new ResponseEntity<>(allAvailableProjectsByUserIn, HttpStatus.ACCEPTED);
    }

    @PostMapping("/create")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectProperties projectProperties,
                                                    HttpServletRequest httpServletRequest,
                                                    @RequestParam String userId) {

        Boolean isUserExists = isUserRegistered(userId, httpServletRequest);

        if (isUserExists) {
            Project project = projectService.createProject(projectProperties, userId);
            ProjectDTO projectDTO = modelMapper.map(project, ProjectDTO.class);
            return new ResponseEntity<>(projectDTO, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProjectDTO> getProject(@RequestBody String uuid) {
        try {
            return new ResponseEntity<>(projectService.getProjectByProjectId(new ProjectId(uuid))
                    .orElseThrow(ProjectNotFoundException::new), HttpStatus.ACCEPTED);
        } catch (ProjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //TODO need to consume projectRole
    @PostMapping("/{uuid}/workers/add")
    public ResponseEntity addUserToProject(@PathVariable String uuid,
                                           @RequestBody HireModel hireModel,
                                           HttpServletRequest httpServletRequest) {

        Boolean isUserExists = isUserRegistered(hireModel.getUserId(), httpServletRequest);

        if (isUserExists) {

            try {
                projectService.addWorker(new ProjectId(uuid), hireModel.getUserId(), hireModel.getProjectRole());
                return new ResponseEntity(HttpStatus.OK);
            } catch (WorkerAlreadyHiredException e) {
                return new ResponseEntity(HttpStatus.CONFLICT);
            }

        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{uuid}/workers")
    public ResponseEntity<Collection<WorkerDto>> getParticipants(@PathVariable String uuid) {
        Collection<WorkerDto> workers = this.projectService.getWorkers(new ProjectId(uuid));
        return new ResponseEntity<>(workers, HttpStatus.OK);

    }

    @GetMapping("/{uuid}/dashboard")
    public ResponseEntity<Map<String, Object>> dashboard(@PathVariable String uuid) {
        try {
            Map map = this.projectService.dashboardProject(new ProjectId(uuid));
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (ProjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{uuid}/repository")
    public ResponseEntity initRepository(@PathVariable String uuid, @RequestBody SourceCodeDto sourceCodeDto){

        try{
            this.projectService.initSourceCodeRepository(new ProjectId(uuid), sourceCodeDto);
            return new ResponseEntity(HttpStatus.OK);
        }catch (ProjectNotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{uuid}/repository")
    public ResponseEntity<SourceCodeDto> getRepository(@PathVariable String uuid){

        try{
            SourceCodeDto sourceCodeDto = this.projectService.getRepository(new ProjectId(uuid));
            return new ResponseEntity<>(sourceCodeDto, HttpStatus.OK);
        }catch (ProjectNotFoundException | NullPointerException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    private Boolean isUserRegistered(@RequestParam String userEmail, HttpServletRequest httpServletRequest) {
//
//        log.info("UserId: " + userEmail);
//
//        HttpHeaders headers = new HttpHeaders();
//
//        log.info("AUTHORIZATION: " + httpServletRequest.getHeader("Authorization"));
//
//        headers.set("Authorization", httpServletRequest.getHeader("Authorization"));
//
//
//        HttpEntity httpEntity = new HttpEntity("parameters", headers);
//        String url = AUTHENTICATION_MICROSERVICE + "/api/auth/check?subjectId=" + userEmail;
//
//        log.info("URL: " + url);

//        Boolean body = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Boolean.class).getBody();
        return true;
    }
}