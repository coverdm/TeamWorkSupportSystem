package com.matuszak.engineer.model.entity;

import com.matuszak.engineer.model.NotificationType;
import lombok.*;
import lombok.extern.java.Log;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Log
@ToString
@Document
public class Notification {

    @Id
    private Integer notificationId;
    private String message;
    private String link;
    private NotificationType type;
    private String created;
    private Boolean read;
}
