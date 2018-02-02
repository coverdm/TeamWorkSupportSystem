package com.matuszak.engineer.domain.project.boundary.web;


import com.matuszak.engineer.domain.project.model.dto.IssueRoomProperties;
import com.matuszak.engineer.domain.project.model.ProjectId;
import com.matuszak.engineer.domain.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/project/{uuid}")
@Log
public class IssuesRoomController {

    private final ProjectService projectService;

    @PostMapping("/issue-room")
    public ResponseEntity createIssueRoom(@PathVariable String uuid,
                                          @RequestBody IssueRoomProperties issueRoomProperties){

        projectService.createIssueRoom(new ProjectId(uuid), issueRoomProperties);

        return new ResponseEntity(HttpStatus.OK);
    }

}
