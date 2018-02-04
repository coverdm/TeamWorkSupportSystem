package com.matuszak.engineer.auth.repository;

import com.matuszak.engineer.auth.model.SubjectId;
import com.matuszak.engineer.auth.model.entity.Subject;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SubjectRepository extends MongoRepository<Subject, SubjectId> {
    Optional<Subject> getSubjectBySubjectId(SubjectId email);
}
