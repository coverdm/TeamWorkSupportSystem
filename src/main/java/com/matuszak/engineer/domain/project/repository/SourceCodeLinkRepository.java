package com.matuszak.engineer.domain.project.repository;

import com.matuszak.engineer.domain.project.model.entity.SourceCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceCodeLinkRepository  extends JpaRepository<SourceCode, Long>{
}
