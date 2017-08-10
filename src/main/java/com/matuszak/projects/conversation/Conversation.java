package com.matuszak.projects.conversation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conversation implements Serializable {

    @Id
    @GeneratedValue
    private String id;

    private String topic;

    @OneToMany
    private Collection<Message> messages;
}