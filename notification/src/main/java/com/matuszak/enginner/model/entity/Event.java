package com.matuszak.enginner.model.entity;

import com.matuszak.enginner.model.EventType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Event {

    private Integer eventId;
    private String message;
    private String link;
    private EventType type;
    private String created;

}
