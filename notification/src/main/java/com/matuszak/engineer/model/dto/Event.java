package com.matuszak.engineer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collection;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Event implements Serializable{
    private NotificationDto notification;
    private Collection<String> receivers;
}
