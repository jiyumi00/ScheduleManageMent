package com.sparta.schedulemanagement.service;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.entity.Schedule;
import com.sparta.schedulemanagement.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository){
        this.scheduleRepository=scheduleRepository;
    }
    //일정 등록
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        //RequestDto -> Entity
        Schedule schedule=new Schedule(requestDto);

        //DB 저장
        Schedule saveSchedule=scheduleRepository.save(schedule);

        //Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto=new ScheduleResponseDto(schedule);
        return scheduleResponseDto;
    }
    //선택한 일정 조회
    public ScheduleResponseDto getSchedule(Long id) {
        //해당 일정이 존재하는 지 확인
        Schedule schedule=scheduleRepository.findById(id);
        if(schedule!=null) {
            ScheduleResponseDto scheduleResponseDto=scheduleRepository.find(id);
            return scheduleResponseDto;
        }else{
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다");
        }
    }
    //일정 수정
    public Long updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        //해당 일정이 DB에 존재하는 지 확인
        Schedule schedule=scheduleRepository.findById(id);
        if(schedule!=null){
           scheduleRepository.updateSchedule(id,scheduleRequestDto);
            return id;
        }else{
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다");
        }
    }
    //전체 일정 조회
    public List<ScheduleResponseDto> getScheduleList() {
        return scheduleRepository.findAll();
    }
    //일정 삭제
    public Long deleteSchedule(Long id) {

        Schedule schedule=scheduleRepository.findById(id);
        //해당 일정이 DB에 존재하는 지 확인
        if(schedule!=null){
            scheduleRepository.delete(id);
            return id;
        }else{
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다");
        }
    }
//    public Schedule findById(Long id){
//        String sql="SELECT * FROM schedule WHERE id = ?";
//
//        return jdbcTemplate.query(sql,resultSet ->{
//            if(resultSet.next()){
//                Schedule schedule=new Schedule();
//                schedule.setName(resultSet.getString("name"));
//                schedule.setTodo(resultSet.getString("todo"));
//                return schedule;
//            }else{
//                return null;
//            }
//        },id);
//    }
}
