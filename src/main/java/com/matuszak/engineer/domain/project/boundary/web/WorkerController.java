package com.matuszak.engineer.domain.project.boundary.web;

import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.model.WorkerId;
import com.matuszak.engineer.domain.project.model.dto.WorkerDto;
import com.matuszak.engineer.domain.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project/{{uuid}}/workers")
public class WorkerController {

    private final ProjectService projectService;
    private final RestTemplate restTemplate;
    private final String HOST = "http://localhost:8080";

//    @GetMapping("/")
//    public ResponseEntity<Collection<WorkerDto>> getWorkersWithMinimalProfile(HttpServletRequest httpServletRequest, @PathVariable String uuid){
//        Collection<WorkerDto> workers = projectService.getWorkers(new ProjectId(uuid));
//        List<WorkerId> collect = workers.stream().map(WorkerDto::getWorkerId).map(WorkerId::new).collect(Collectors.toList());
//        this.getProfilesByWorkersId(httpServletRequest, collect);
//    }
//
//    private void getProfilesByWorkersId(HttpServletRequest httpServletRequest, Collection<WorkerId> workerIds){
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", httpServletRequest.getHeader("Authorization"));
//
//        HttpEntity httpEntity = new HttpEntity("parameters", headers);
//        String url = HOST + "/api/profiles/minWorkers";
//
//        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, Boolean.class).getBody();
//    }

    @GetMapping("/")
    public void sth(){

    }

}
