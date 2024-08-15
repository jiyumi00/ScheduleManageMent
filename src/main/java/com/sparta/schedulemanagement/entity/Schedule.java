package com.sparta.schedulemanagement.entity;
import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import lombok.Data;

@Data
public class Schedule {
    private Long id;
    private String name;
    private String password;
    private String todo;
    private String date;

    public Schedule(ScheduleRequestDto scheduleRequestDto){
        this.name= scheduleRequestDto.getName();
        this.password=scheduleRequestDto.getPassword();
        this.todo=scheduleRequestDto.getTodo();
        this.date=scheduleRequestDto.getDate();
    }
}
