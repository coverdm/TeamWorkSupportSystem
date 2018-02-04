package com.matuszak.engineer.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
public class WorkerId implements Serializable{

    @Field(value = "worker_id")
    private String workerId;

    private WorkerId() {
    }

    public static String generate(){
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        return Long.toString(l, Character.MAX_RADIX);
    }

}