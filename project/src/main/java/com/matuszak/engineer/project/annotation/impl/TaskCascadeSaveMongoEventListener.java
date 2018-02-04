package com.matuszak.engineer.project.annotation.impl;

import com.matuszak.engineer.project.model.entity.Task;
import com.matuszak.engineer.project.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskCascadeSaveMongoEventListener extends CascadingMongoEventListener{

    @Autowired
    private TaskRepository taskRepository;

    @Override
    protected void updateEntity(Object fieldValue) {
        if(fieldValue instanceof Task){

            Task task = (Task) fieldValue;

            if(task.getTaskId() == null)
                taskRepository.save(task);

        }
    }
}
