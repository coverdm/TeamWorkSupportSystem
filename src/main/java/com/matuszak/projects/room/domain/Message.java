package com.matuszak.projects.room.domain;

import com.matuszak.projects.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User author;

    private String message;
}