package com.sparta.schedulemanagement.dto;


import com.sparta.schedulemanagement.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String name;
    private String todo;
    private String date;
    private String managerName;
    private String updateAt;

    public ScheduleResponseDto(Schedule schedule){
        this.id=schedule.getId();
        this.name=schedule.getName();
        this.todo=schedule.getTodo();
        this.date=schedule.getDate();
        this.managerName=schedule.getManagerName();
    }
    public ScheduleResponseDto(Long id,String name,String todo,String date,String managerName){
        this.id=id;
        this.name=name;
        this.todo=todo;
        this.date=date;
        this.managerName=managerName;
    }
}
