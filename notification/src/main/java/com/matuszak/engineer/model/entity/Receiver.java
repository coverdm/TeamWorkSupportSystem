package com.matuszak.engineer.model.entity;

import com.matuszak.engineer.model.ReceiverId;
import com.matuszak.engineer.model.dto.NotificationDto;
import com.matuszak.engineer.model.exception.NotificationNotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

@Document
@Data
@NoArgsConstructor
@ToString
public class Receiver {

    @Id
    private ReceiverId receiverId;

    private Collection<Notification> notifications;

    public Receiver(ReceiverId receiverId) {
        this.receiverId = receiverId;
        this.notifications = new ArrayList<>();
    }

    public void addNotification(NotificationDto notificationDto){

        Notification notification = Notification.builder()
                .notificationId(notificationDto.getNotificationId())
                .link(notificationDto.getLink())
                .created(String.valueOf(Instant.now().getNano()))
                .message(notificationDto.getMessage())
                .read(Boolean.FALSE)
                .type(notificationDto.getType())
                .build();

        this.notifications.add(notification);
    }

    public void markAsReadNotification(Integer notificationId){
        notifications.stream()
                .filter(notification -> notification.getNotificationId().equals(notificationId))
                .findAny()
                .orElseThrow(NotificationNotFoundException::new)
                .setRead(Boolean.TRUE);
    }

}
