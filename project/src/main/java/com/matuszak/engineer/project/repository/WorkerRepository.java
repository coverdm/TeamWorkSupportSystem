package com.matuszak.engineer.project.repository;

import com.matuszak.engineer.project.model.WorkerId;
import com.matuszak.engineer.project.model.entity.Worker;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;

public interface WorkerRepository extends MongoRepository<Worker, WorkerId> {
    Collection<Worker> getWorkerByWorkerId(WorkerId workerId);
}
