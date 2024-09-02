package com.sparta.schedulemanagement.dto;


import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String name;
    private String password;
    private String todo;
    private String date;
    private String managerName;
}
