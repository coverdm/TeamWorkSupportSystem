package com.matuszak.engineer.domain.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IssueRoomProperties {
    private QuestionDto question;
    private String taskId; // <- Optional property
}
