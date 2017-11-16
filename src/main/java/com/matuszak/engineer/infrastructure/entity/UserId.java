package com.matuszak.engineer.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Embeddable
public class UserId implements Serializable{

    private String userId;

    private UserId() {
    }

    public static String generate(){
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        return Long.toString(l, Character.MAX_RADIX);
    }

}