package com.matuszak.engineer.domain.project.model;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.UUID;

@Getter
@Embeddable
public class TaskId implements Serializable{
    private String id;

    public TaskId(){
        this.id = generate();
    }

    public TaskId(String id) {
        this.id = id;
    }

    public static String generate(){
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        return Long.toString(l, Character.MAX_RADIX);
    }

}
