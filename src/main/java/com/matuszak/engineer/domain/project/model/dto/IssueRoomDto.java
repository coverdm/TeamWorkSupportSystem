package com.matuszak.engineer.domain.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueRoomDto {
    private QuestionDto questionDto;
    private Collection<AnswerDto> answers;
}
