package com.matuszak.engineer;

import com.matuszak.engineer.model.NotificationType;
import com.matuszak.engineer.model.ReceiverId;
import com.matuszak.engineer.model.dto.NotificationDto;
import com.matuszak.engineer.model.entity.Receiver;
import com.matuszak.engineer.repository.ReceiverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class DumbValues implements ApplicationListener<ContextRefreshedEvent> {

    private final ReceiverRepository receiverRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        receiverRepository.deleteAll();
        receiverRepository.save(Arrays.asList(
                new Receiver(new ReceiverId("dawid_matuszak@outlook.com")),
                new Receiver(new ReceiverId("michal_golabek@interia.pl"))
        ));
    }
}
