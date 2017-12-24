package com.matuszak.engineer.domain.project.repository;

import com.matuszak.engineer.domain.project.model.entity.Worker;
import com.matuszak.engineer.infrastructure.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Collection<Worker> getWorkerByUserId(UserId userId);
}
