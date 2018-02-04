package com.matuszak.engineer.project.annotation.impl;

import com.matuszak.engineer.project.model.entity.Worker;
import com.matuszak.engineer.project.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class WorkersCascadeSaveMongoEventListener extends CascadingMongoEventListener{

    @Autowired
    private WorkerRepository workerRepository;

    @Override
    protected void updateEntity(Object fieldValue) {

        if(fieldValue instanceof Worker){
            Worker worker = (Worker) fieldValue;

            if(worker.getWorkerId() == null){
                workerRepository.save(worker);
            }
        }

    }
}
