package com.sparta.schedulemanagement.controller;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.service.ScheduleService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {
    private final JdbcTemplate jdbcTemplate;


    public ScheduleController(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }
    //일정 등록
    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto){
        ScheduleService scheduleService=new ScheduleService(jdbcTemplate);
        return scheduleService.createSchedule(requestDto);
    }

    //선택한 일정 조회
    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id){
        ScheduleService scheduleService=new ScheduleService(jdbcTemplate);
        return scheduleService.getSchedule(id);
    }

    //전체 일정 목록 조회
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getScheduleList(){
        ScheduleService scheduleService=new ScheduleService(jdbcTemplate);
        return scheduleService.getScheduleList();
    }

    //선택한 일정 수정
    //비밀번호 확인
    @PutMapping("/schedules/{id}")
    public Long updateSchedule(@PathVariable Long id,@RequestBody ScheduleRequestDto scheduleRequestDto){
        ScheduleService scheduleService=new ScheduleService(jdbcTemplate);
        return scheduleService.updateSchedule(id,scheduleRequestDto);
    }

    //선택한 일정 삭제
    @DeleteMapping("/schedules/{id}")
    public Long deleteSchedule(@PathVariable Long id){
        ScheduleService scheduleService=new ScheduleService(jdbcTemplate);
        return scheduleService.deleteSchedule(id);
    }
}
