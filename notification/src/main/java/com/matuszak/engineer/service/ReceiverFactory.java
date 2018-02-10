package com.matuszak.engineer.service;

import com.matuszak.engineer.model.ReceiverId;
import com.matuszak.engineer.model.entity.Receiver;
import com.matuszak.engineer.repository.ReceiverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceiverFactory {

    private final ReceiverRepository receiverRepository;

    public void createReceiver(String userId){
        receiverRepository.save(new Receiver(new ReceiverId(userId)));
    }

}
