package com.matuszak.engineer.domain.project.model.entity;

import com.matuszak.engineer.domain.project.model.SourceCodeHolderLink;
import com.matuszak.engineer.domain.project.model.SourceCodeRepositoryHolderType;
import com.matuszak.engineer.infrastructure.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SourceCode extends BaseEntity{

    @Enumerated
    private SourceCodeRepositoryHolderType sourceCodeRepositoryHolderType;
    private SourceCodeHolderLink sourceCodeHolderLink;
}
