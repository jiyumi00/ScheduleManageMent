package com.sparta.schedulemanagement.controller;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService=scheduleService;
    }
    //일정 등록
    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto){
        return scheduleService.createSchedule(requestDto);
    }

    //선택한 일정 조회
    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id){
        return scheduleService.getSchedule(id);
    }

    //전체 일정 목록 조회
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getScheduleList(){
        return scheduleService.getScheduleList();
    }

    //선택한 일정 수정
    //비밀번호 확인
    @PutMapping("/schedules/{id}")
    public Long updateSchedule(@PathVariable Long id,@RequestBody ScheduleRequestDto scheduleRequestDto){
        return scheduleService.updateSchedule(id,scheduleRequestDto);
    }

    //선택한 일정 삭제
    @DeleteMapping("/schedules/{id}")
    public Long deleteSchedule(@PathVariable Long id){
        return scheduleService.deleteSchedule(id);
    }
}
