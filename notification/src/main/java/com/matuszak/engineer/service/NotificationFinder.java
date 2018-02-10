package com.matuszak.engineer.service;

import com.matuszak.engineer.model.ReceiverId;
import com.matuszak.engineer.model.dto.NotificationDto;
import com.matuszak.engineer.model.entity.Receiver;
import com.matuszak.engineer.repository.ReceiverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log
public class NotificationFinder {

    private final ReceiverRepository receiverRepository;
    private final ModelMapper modelMapper;

    public Collection<NotificationDto> getNotifications(String receiverId) {

        log.info(receiverId);

        Receiver one = receiverRepository.findOne(new ReceiverId(receiverId));

        log.info(one.toString());

        List<NotificationDto> collect = one.getNotifications()
                .stream()
                .filter(notification -> !notification.getRead())
                .map(notification -> modelMapper.map(notification, NotificationDto.class))
                .collect(Collectors.toList());
        return collect;
    }

}
