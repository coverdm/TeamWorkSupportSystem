package com.matuszak.engineer.facade;

import com.matuszak.engineer.model.dto.Event;
import com.matuszak.engineer.model.dto.NotificationDto;
import com.matuszak.engineer.service.BasicOperationsService;
import com.matuszak.engineer.service.NotificationBroadcaster;
import com.matuszak.engineer.service.NotificationFinder;
import com.matuszak.engineer.service.ReceiverFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class NotificationFacade {

    private final NotificationFinder notificationFinder;
    private final NotificationBroadcaster notificationBroadcaster;
    private final BasicOperationsService basicOperationsService;
    private final ReceiverFactory receiverFactory;

    public Collection<NotificationDto> getNotifications(String receiverId){
        return notificationFinder.getNotifications(receiverId);
    }

    public void publish(Event event){
        notificationBroadcaster.notify(event);
    }

    public void markAsRead(Integer notificationId, String receiverId){
        basicOperationsService.markAsReadNotification(notificationId, receiverId);
    }

    public void createReceiver(String userId){
        receiverFactory.createReceiver(userId);
    }

}
