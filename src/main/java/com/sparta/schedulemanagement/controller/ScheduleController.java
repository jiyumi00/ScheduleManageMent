package com.sparta.schedulemanagement.controller;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.entity.Schedule;
import com.sparta.schedulemanagement.entity.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleController(ScheduleRepository scheduleRepository){
        this.scheduleRepository=scheduleRepository;
    }
    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto){
        //RequestDto -> Entity
        Schedule schedule=new Schedule(requestDto);
        //DB 저장
        scheduleRepository.save(schedule);

        //Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto=new ScheduleResponseDto(schedule);
        return scheduleResponseDto;
    }

}
