package com.matuszak.engineer.domain.project.repository;

import com.matuszak.engineer.domain.project.model.WorkerId;
import com.matuszak.engineer.domain.project.model.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface WorkerRepository extends JpaRepository<Worker, WorkerId>{
    Collection<Worker> getWorkerByWorkerId(WorkerId workerId);
}
