package com.matuszak.engineer.domain.project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@Getter
public class TaskId implements Serializable{

    private String taskId;

    public TaskId(String shortId) {
        this.taskId = shortId;
    }

    public static String generateShortId(){
        return RandomStringUtils.random(8, UUID.randomUUID().toString());
    }
}
