package com.matuszak.engineer.domain.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
@Embeddable
public class WorkerId implements Serializable{

    private String workerId;

    private WorkerId() {
    }

    public static String generate(){
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        return Long.toString(l, Character.MAX_RADIX);
    }

}