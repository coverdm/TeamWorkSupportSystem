package com.matuszak.engineer.service;

import com.matuszak.engineer.model.ReceiverId;
import com.matuszak.engineer.model.entity.Receiver;
import com.matuszak.engineer.model.exception.NotificationNotFoundException;
import com.matuszak.engineer.repository.ReceiverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log
public class BasicOperationsService {

    private final ReceiverRepository receiverRepository;

    public void markAsReadNotification(Integer id, String receiverId) throws NotificationNotFoundException {

        Receiver one = receiverRepository.findOne(new ReceiverId(receiverId));

        if (one != null) {
            one.markAsReadNotification(id);
            receiverRepository.save(one);
        } else
            throw new NotificationNotFoundException("Notification " + id + " not found");
    }
}
