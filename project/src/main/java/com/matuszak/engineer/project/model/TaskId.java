package com.matuszak.engineer.project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.Serializable;
import java.util.UUID;

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
