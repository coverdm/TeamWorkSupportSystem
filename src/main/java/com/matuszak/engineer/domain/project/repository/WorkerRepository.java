package com.matuszak.engineer.domain.project.repository;

import com.matuszak.engineer.domain.project.model.entity.Worker;
import com.matuszak.engineer.domain.project.model.WorkerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Collection<Worker> getWorkerByWorkerId(WorkerId workerId);
    Collection<Worker> getWorkersByWorkerIdIn(Collection<WorkerId> workerIds);
}
