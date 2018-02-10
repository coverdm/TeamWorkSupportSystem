package com.matuszak.engineer.project.boundary.web;


import com.matuszak.engineer.project.exceptions.IssueRoomNotFoundException;
import com.matuszak.engineer.project.exceptions.ProjectNotFoundException;
import com.matuszak.engineer.project.model.IssueRoomId;
import com.matuszak.engineer.project.model.ProjectId;
import com.matuszak.engineer.project.model.dto.AnswerDto;
import com.matuszak.engineer.project.model.dto.IssueRoomDto;
import com.matuszak.engineer.project.model.dto.IssueRoomProperties;
import com.matuszak.engineer.project.service.ProjectFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/{uuid}")
@Log
public class IssuesRoomController {

    private final ProjectFacade projectFacade;

    @PostMapping("/issue-room")
    public ResponseEntity<IssueRoomDto> createIssueRoom(@PathVariable String uuid,
                                                        @RequestBody IssueRoomProperties issueRoomProperties){
        try{
            IssueRoomDto issueRoom = projectFacade.createIssueRoom(new ProjectId(uuid), issueRoomProperties);
            return new ResponseEntity<>(issueRoom, HttpStatus.OK);
        }catch (ProjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/issue-rooms")
    public ResponseEntity<Collection<IssueRoomDto>> getIssueRooms(@PathVariable String uuid){
        try{
            Collection<IssueRoomDto> issueRooms = projectFacade.getIssueRooms(new ProjectId(uuid));
            return new ResponseEntity<>(issueRooms, HttpStatus.OK);
        }catch (ProjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/issue-room")
    public ResponseEntity<IssueRoomDto> getIssueRoom(@PathVariable String uuid, @RequestParam String id){
        try{
            IssueRoomDto issueRoom = projectFacade.getIssueRoom(new ProjectId(uuid), new IssueRoomId(id));
            return new ResponseEntity<>(issueRoom, HttpStatus.OK);
        }catch (ProjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/issue-room")
    public ResponseEntity<IssueRoomDto> closeIssueRoom(@PathVariable String uuid, @RequestParam String id){
        try{
            projectFacade.closeIssueRoom(new ProjectId(uuid), new IssueRoomId(id));
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (ProjectNotFoundException | IssueRoomNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/issue-room/answer")
    public ResponseEntity<IssueRoomDto> answer(@PathVariable String uuid,
                                               @RequestParam String id,
                                               @RequestBody AnswerDto answerDto){
        try{
            projectFacade.addAnswerIssueRoom(new ProjectId(uuid), new IssueRoomId(id), answerDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (ProjectNotFoundException | IssueRoomNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
