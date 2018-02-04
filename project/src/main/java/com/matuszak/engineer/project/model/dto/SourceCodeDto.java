package com.matuszak.engineer.project.model.dto;


import com.matuszak.engineer.project.model.SourceCodeHolderLink;
import com.matuszak.engineer.project.model.SourceCodeRepositoryHolderType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString
public class SourceCodeDto {

    private SourceCodeHolderLink sourceCodeHolderLink;
    private SourceCodeRepositoryHolderType sourceCodeRepositoryHolderType;

}
