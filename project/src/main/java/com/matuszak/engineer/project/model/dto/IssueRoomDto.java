package com.matuszak.engineer.project.model.dto;

import com.matuszak.engineer.project.model.QuestionStatus;
import lombok.*;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class IssueRoomDto {
    private String issueRoomId;
    private String title;
    private QuestionDto question;
    private QuestionStatus questionStatus;
    private Collection<AnswerDto> answers;
}
