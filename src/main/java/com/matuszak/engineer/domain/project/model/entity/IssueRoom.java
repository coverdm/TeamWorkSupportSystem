package com.matuszak.engineer.domain.project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IssueRoom {

    @Id
    @GeneratedValue
    private Long id;
    private Question question;

    @ElementCollection
    private Collection<Answer> answerCollection;
}
