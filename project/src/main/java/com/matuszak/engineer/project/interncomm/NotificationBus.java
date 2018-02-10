package com.matuszak.engineer.project.interncomm;

import com.matuszak.engineer.project.model.dto.Event;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("notification")
public interface NotificationBus {

    @RequestMapping(value = "/event", method = RequestMethod.POST)
    ResponseEntity notify(Event event);

}
