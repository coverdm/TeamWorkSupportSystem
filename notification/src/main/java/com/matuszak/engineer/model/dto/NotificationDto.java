package com.matuszak.engineer.model.dto;

import com.matuszak.engineer.model.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotificationDto implements Serializable{

    private Integer notificationId;
    private String message;
    private String link;
    private NotificationType type;
    private String created;
    private Boolean read;

}
