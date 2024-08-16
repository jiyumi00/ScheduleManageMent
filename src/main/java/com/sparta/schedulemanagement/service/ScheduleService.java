package com.sparta.schedulemanagement.service;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.entity.Schedule;
import com.sparta.schedulemanagement.repository.ScheduleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ScheduleService {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        //RequestDto -> Entity
        Schedule schedule=new Schedule(requestDto);

        //DB 저장
        ScheduleRepository scheduleRepository=new ScheduleRepository(jdbcTemplate);
        Schedule saveSchedule=scheduleRepository.save(schedule);

        //Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto=new ScheduleResponseDto(schedule);
        return scheduleResponseDto;
    }

    public ScheduleResponseDto getSchedule(Long id) {
        //해당 일정이 존재하는 지 확인
        Schedule schedule=findById(id);
        if(schedule!=null) {
            String sql="SELECT * FROM schedule WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new RowMapper<ScheduleResponseDto>() {
                @Override
                public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new ScheduleResponseDto(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("todo"),
                            rs.getString("date")
                    );
                }
            },id);
        }else{
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다");
        }
    }



    public Long updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        //해당 일정이 DB에 존재하는 지 확인
        ScheduleRepository scheduleRepository=new ScheduleRepository(jdbcTemplate);
        Schedule schedule=scheduleRepository.findById(id);
        if(schedule!=null){
           scheduleRepository.updateSchedule(id,scheduleRequestDto);
            return id;
        }else{
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다");
        }
    }

    public List<ScheduleResponseDto> getScheduleList() {
        ScheduleRepository scheduleRepository=new ScheduleRepository(jdbcTemplate);
        return scheduleRepository.findAll();
    }

    public Long deleteSchedule(Long id) {
        ScheduleRepository scheduleRepository=new ScheduleRepository(jdbcTemplate);

        Schedule schedule=scheduleRepository.findById(id);
        //해당 일정이 DB에 존재하는 지 확인
        if(schedule!=null){
            scheduleRepository.delete(id);
            return id;
        }else{
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다");
        }
    }
    public Schedule findById(Long id){
        //DB 조회
        String sql="SELECT * FROM schedule WHERE id = ?";

        return jdbcTemplate.query(sql,resultSet ->{
            if(resultSet.next()){
                Schedule schedule=new Schedule();
                schedule.setName(resultSet.getString("name"));
                schedule.setTodo(resultSet.getString("todo"));
                return schedule;
            }else{
                return null;
            }
        },id);
    }
}
