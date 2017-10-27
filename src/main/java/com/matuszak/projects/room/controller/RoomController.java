package com.matuszak.projects.room.controller;

import com.matuszak.projects.room.domain.Room;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log
@RequestMapping("/room")
public class RoomController {

    @GetMapping("/room/{id}")
    public Room getRoomById(@PathVariable Integer id){
        return new Room();
    }

}
