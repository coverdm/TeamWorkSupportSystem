package com.matuszak.engineer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ToString
public class ReceiverId implements Serializable{
    private String id;
}
