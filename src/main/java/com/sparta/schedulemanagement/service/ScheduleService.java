package com.sparta.schedulemanagement.service;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        KeyHolder keyHolder=new GeneratedKeyHolder(); //기본 키를 반환받기 위한 객체

        String sql="INSERT INTO schedule (name, password, todo, date) VALUES (?,?,?,?)";

        jdbcTemplate.update(con ->{
            PreparedStatement preparedStatement=con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,schedule.getName());
            preparedStatement.setString(2,schedule.getPassword());
            preparedStatement.setString(3,schedule.getTodo());
            preparedStatement.setString(4,schedule.getDate());

            return preparedStatement;
        },keyHolder);

        Long id=keyHolder.getKey().longValue();
        schedule.setId(id);

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

    private Schedule findById(Long id){
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

    public Long updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        //해당 일정이 DB에 존재하는 지 확인
        Schedule schedule=findById(id);
        if(schedule!=null){
            String sql="UPDATE schedule SET name = ?, todo = ? WHERE id = ?";
            jdbcTemplate.update(sql,scheduleRequestDto.getName(),scheduleRequestDto.getTodo(),id);

            //일정 수정
            return id;
        }else{
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다");
        }
    }

    public List<ScheduleResponseDto> getScheduleList() {
        //DB 조회
        String sql="SELECT * FROM schedule";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id=rs.getLong("id");
                String name=rs.getString("name");
                String todo=rs.getString("todo");
                String date=rs.getString("date");
                return new ScheduleResponseDto(id,name,todo,date);
            }
        });
    }

    public Long deleteSchedule(Long id) {
        Schedule schedule=findById(id);
        //해당 일정이 DB에 존재하는 지 확인
        if(schedule!=null){
            String sql="DELETE FROM schedule WHERE id = ?";
            jdbcTemplate.update(sql,id);

            return id;
        }else{
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다");
        }
    }
}
