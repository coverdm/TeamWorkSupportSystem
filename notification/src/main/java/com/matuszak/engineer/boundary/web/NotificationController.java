package com.matuszak.engineer.boundary.web;

import com.matuszak.engineer.facade.NotificationFacade;
import com.matuszak.engineer.interncomm.SecurityAuthenticationChecker;
import com.matuszak.engineer.model.ReceiverId;
import com.matuszak.engineer.model.dto.Event;
import com.matuszak.engineer.model.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationFacade notificationFacade;
    private final SecurityAuthenticationChecker securityAuthenticationChecker;

    @GetMapping("/")
    public ResponseEntity<Collection<NotificationDto>> getNotifications(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authenticatedUser = securityAuthenticationChecker.getAuthenticatedUser(request.getHeader("Authorization"));
        return new ResponseEntity<>(notificationFacade.getNotifications(authenticatedUser), HttpStatus.OK);
    }

    @PostMapping("/event")
    public ResponseEntity storeEvent(@RequestBody Event event){
        notificationFacade.publish(event);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity markAsReadNotification(@PathVariable String notificationId, @RequestBody ReceiverId receiverId){
        notificationFacade.markAsRead(Integer.parseInt(notificationId),receiverId.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/createReceiver")
    public ResponseEntity createReceiver(@RequestBody ReceiverId receiverId){
        notificationFacade.createReceiver(receiverId.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

}
