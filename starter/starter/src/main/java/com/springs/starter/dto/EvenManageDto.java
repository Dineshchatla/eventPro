package com.springs.starter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class EvenManageDto {
    private int id;
    private String name;
    private Date StratDate;
    private String status;
}
