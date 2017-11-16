package com.matuszak.engineer.domain.auth.repository;

import com.matuszak.engineer.domain.auth.model.entity.Subject;
import com.matuszak.engineer.domain.auth.model.entity.SubjectId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, SubjectId> {
    Optional<Subject> getSubjectByEmail(String email);
    Optional<Subject> getSubjectBySubjectId(SubjectId userId);
}
