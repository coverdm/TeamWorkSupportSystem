package com.matuszak.engineer.service;

import org.springframework.stereotype.Service;

@Service
public class EventIdentificationProviderService {

    private static Integer eventId;

    static{
        eventId = 0;
    }

    public synchronized Integer generateEventId(){
        return eventId++;
    }

}
