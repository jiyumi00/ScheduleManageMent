package com.sparta.schedulemanagement.controller;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.entity.Schedule;
import com.sparta.schedulemanagement.entity.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleController(ScheduleRepository scheduleRepository){
        this.scheduleRepository=scheduleRepository;
    }

    //일정 등록
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

    //선택한 일정 조회
    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id){
        Schedule schedule=scheduleRepository.findById(id);
        return new ScheduleResponseDto(schedule);
    }

    //전체 일정 목록 조회
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getScheduleList(){
        return scheduleRepository.findAll();
    }

    //선택한 일정 수정
    //비밀번호 확인
    @PutMapping("/schedules/{id}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id,@RequestBody ScheduleRequestDto scheduleRequestDto){
        //해당 일정이 DB에 존재하는 지 확인
        if(scheduleRepository.findById(id)!=null){
            //해당 일정 가져오기
            Schedule schedule=scheduleRepository.findById(id);
            //일정 수정
            schedule.update(scheduleRequestDto);
            return new ScheduleResponseDto(schedule);
        }else{
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다");
        }
    }

    //선택한 일정 삭제
    @DeleteMapping("/schedules/{id}")
    public Long deleteSchedule(@PathVariable Long id){
        //해당 일정이 DB에 존재하는 지 확인
        if(scheduleRepository.findById(id)!=null){
            //해당 일정 삭제
           scheduleRepository.remove(id);
            return id;
        }else{
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다");
        }
    }

}
