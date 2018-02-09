package com.matuszak.engineer.project.model.entity;

import com.matuszak.engineer.project.model.SourceCodeHolderLink;
import com.matuszak.engineer.project.model.SourceCodeRepositoryHolderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SourceCode{

    @Id
    private SourceCodeRepositoryHolderType sourceCodeRepositoryHolderType;
    private SourceCodeHolderLink sourceCodeHolderLink;
}
