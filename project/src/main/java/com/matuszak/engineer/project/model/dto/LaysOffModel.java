package com.matuszak.engineer.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class LaysOffModel {
    private String workerId;
    private String cause;
}
