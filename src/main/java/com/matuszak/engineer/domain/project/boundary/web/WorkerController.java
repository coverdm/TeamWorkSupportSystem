package com.matuszak.engineer.domain.project.boundary.web;

import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.WorkerId;
import com.matuszak.engineer.domain.project.model.dto.WorkerDto;
import com.matuszak.engineer.domain.project.model.dto.WorkerMinProfile;
import com.matuszak.engineer.domain.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Log
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project/{{uuid}}/workers")
public class WorkerController {

    private final ProjectService projectService;
    private final RestTemplate restTemplate;
    private final String HOST = "http://localhost:8080";

    @GetMapping("/")
    public ResponseEntity<Collection<WorkerDto>> getWorkersWithMinimalProfile(HttpServletRequest httpServletRequest, @PathVariable String uuid){

        log.info("GetWorkersWithMinimalProfile");
        Collection<WorkerDto> workers = projectService.getWorkers(new ProjectId(uuid));


        List<WorkerId> collect = workers.stream().map(WorkerDto::getWorkerId).map(WorkerId::new).collect(Collectors.toList());
        List<WorkerMinProfile> profilesByWorkersId = this.getProfilesByWorkersId(httpServletRequest, collect);

        for(WorkerDto workerDto: workers){
            for(WorkerMinProfile workerMinProfile: profilesByWorkersId){
                if(workerDto.getWorkerId().equals(workerMinProfile.getProfileId()))
                    workerDto.setName(workerMinProfile.getName());
            }
        }

        return new ResponseEntity<>(workers, HttpStatus.OK);
    }

    private List<WorkerMinProfile> getProfilesByWorkersId(HttpServletRequest httpServletRequest, Collection<WorkerId> workerIds){

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", httpServletRequest.getHeader("Authorization"));

        HttpEntity httpEntity = new HttpEntity("parameters", headers);

        StringBuilder stringBuilder = new StringBuilder();

        workerIds.forEach(e -> stringBuilder.append("profileId=" + e.getWorkerId() + "&"));
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("&"));


        String url = HOST + "/api/profiles/minProfile?" + stringBuilder.toString();


        List<WorkerMinProfile> body = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<WorkerMinProfile>>() {
        }).getBody();

        log.info(body.toString());

        return body;
    }

}