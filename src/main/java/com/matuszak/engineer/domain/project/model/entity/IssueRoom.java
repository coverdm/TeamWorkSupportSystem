package com.matuszak.engineer.domain.project.model.entity;

import com.matuszak.engineer.domain.project.model.IssueRoomId;
import com.matuszak.engineer.domain.project.model.QuestionStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class IssueRoom {

    @EmbeddedId
    private IssueRoomId issueRoomId;
    private Question question;

    @Enumerated(EnumType.STRING)
    private QuestionStatus questionStatus;

    @ElementCollection
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
