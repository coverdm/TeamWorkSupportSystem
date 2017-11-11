package com.matuszak.engineer.domain.auth.repository;

import com.matuszak.engineer.domain.auth.model.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> getSubjectByEmail(String email);
    Optional<Subject> getSubjectByUsername(String username);
}
