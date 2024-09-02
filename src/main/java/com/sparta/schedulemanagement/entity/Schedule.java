package com.sparta.schedulemanagement.entity;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long id;
    private String name;
    private String password;
    private String todo;
    private String date;
    private String managerName;

    public Schedule(ScheduleRequestDto scheduleRequestDto){
        this.name= scheduleRequestDto.getName();
        this.password=scheduleRequestDto.getPassword();
        this.todo=scheduleRequestDto.getTodo();
        this.date=scheduleRequestDto.getDate();
        this.managerName=scheduleRequestDto.getManagerName();
    }

    public void update(ScheduleRequestDto scheduleRequestDto){
        this.name=scheduleRequestDto.getName();
        this.todo=scheduleRequestDto.getTodo();
    }
}
