package com.matuszak.engineer.domain.auth.model.entity;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.UUID;

@Getter
@Embeddable
public class SubjectId implements Serializable{

    private String id;

    public SubjectId(){
        this.id = generate();
    }

    public SubjectId(String id) {
        this.id = id;
    }

    public static String generate(){
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        return Long.toString(l, Character.MAX_RADIX);
    }

}
