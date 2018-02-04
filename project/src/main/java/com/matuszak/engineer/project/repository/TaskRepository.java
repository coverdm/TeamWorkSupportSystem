package com.matuszak.engineer.project.repository;

import com.matuszak.engineer.project.model.TaskId;
import com.matuszak.engineer.project.model.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, TaskId> {
}
