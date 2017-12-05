package com.matuszak.engineer.domain.project.service;

import com.matuszak.engineer.domain.project.model.TaskProperties;
import com.matuszak.engineer.domain.project.model.entity.Task;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class TaskFactory {

    public Task createTask(TaskProperties taskProperties){
        log.info("Creating new task...");
        return new Task(taskProperties);
    }

}
