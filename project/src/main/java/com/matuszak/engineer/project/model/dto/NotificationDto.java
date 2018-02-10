package com.matuszak.engineer.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@ToString
@Data
public class NotificationDto implements Serializable {
    private String message;
    private String link;
    private String type;
}
