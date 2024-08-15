package com.sparta.schedulemanagement.dto;


import com.sparta.schedulemanagement.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String name;
    private String todo;
    private String date;

    public ScheduleResponseDto(Schedule schedule){
        this.id=schedule.getId();
        this.name=schedule.getName();
        this.todo=schedule.getTodo();
        this.date=schedule.getDate();
    }
}
