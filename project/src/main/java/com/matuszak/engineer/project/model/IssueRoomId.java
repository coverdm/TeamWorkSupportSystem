package com.matuszak.engineer.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.UUID;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IssueRoomId implements Serializable {
    private String id;

    public static String generate(){
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        return Long.toString(l, Character.MAX_RADIX);
    }
}
