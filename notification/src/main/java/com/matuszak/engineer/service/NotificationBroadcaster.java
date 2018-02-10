package com.matuszak.engineer.service;

import com.matuszak.engineer.model.ReceiverId;
import com.matuszak.engineer.model.dto.Event;
import com.matuszak.engineer.model.entity.Receiver;
import com.matuszak.engineer.repository.ReceiverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Log
@Service
@RequiredArgsConstructor
public class NotificationBroadcaster {

    private final ReceiverRepository receiverRepository;
    private final EventIdentificationProviderService eventIdentificationProviderService;

    public void notify(Event event) {

        Collection<String> receivers = event.getReceivers();

        log.info("########################################################");
        log.info(event.toString());
        log.info(receivers.toString());

        for (String receiver : receivers) {
            Receiver one = receiverRepository.findOne(new ReceiverId(receiver));

            log.info("ONE: " + one.toString());

            if (one != null) {

                event.getNotification()
                        .setNotificationId(eventIdentificationProviderService.generateEventId());

                log.info("event updated " + event.toString());

                one.addNotification(event.getNotification());

                log.info("ONE ADDED NOTIFY " + one.toString());

                receiverRepository.save(one);
            }
        }
    }

}
