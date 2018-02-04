package com.matuszak.engineer.project.model.entity;

import com.matuszak.engineer.project.model.IssueRoomId;
import com.matuszak.engineer.project.model.QuestionStatus;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class IssueRoom {

    private IssueRoomId issueRoomId;
    private Question question;

    private QuestionStatus questionStatus;

    private Collection<Answer> answers;

    public String getIssueRoomId(){
        return this.issueRoomId.getId();
    }

    public void closeAsSolved(){
        questionStatus = QuestionStatus.SOLVED;
    }

    public void addAnswer(Answer answer){
        answers.add(answer);
    }
}
