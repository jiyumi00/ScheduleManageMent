package com.sparta.schedulemanagement.repository;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate=jdbcTemplate;
    }
    //DB저장
    public Schedule save(Schedule schedule) {
        KeyHolder keyHolder=new GeneratedKeyHolder(); //기본 키를 반환받기 위한 객체

        String sql="INSERT INTO schedule (name,password, todo, date,manager_name) VALUES (?,?,?,?,?)";

        jdbcTemplate.update(con ->{
            PreparedStatement preparedStatement=con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,schedule.getName());
            preparedStatement.setString(2,schedule.getPassword());
            preparedStatement.setString(3,schedule.getTodo());
            preparedStatement.setString(4,schedule.getDate());
            preparedStatement.setString(5, schedule.getManagerName());

            return preparedStatement;
        },keyHolder);

        Long id=keyHolder.getKey().longValue();
        schedule.setId(id);
        return schedule;
    }
    //선택한 id 조회
    public Schedule findById(Long id){
        String sql="SELECT * FROM schedule WHERE id = ?";

        return jdbcTemplate.query(sql,resultSet ->{
            if(resultSet.next()){
                Schedule schedule=new Schedule();
                schedule.setPassword(resultSet.getString("password"));
                schedule.setName(resultSet.getString("name"));
                schedule.setTodo(resultSet.getString("todo"));
                return schedule;
            }else{
                return null;
            }
        },id);
    }
    //전체 조회
    public List<ScheduleResponseDto> findAll() {
        //DB 조회
        String sql="SELECT * FROM schedule";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id=rs.getLong("id");
                String name=rs.getString("name");
                String todo=rs.getString("todo");
                String date=rs.getString("date");
                String managerName=rs.getString("manager_name");
                return new ScheduleResponseDto(id,name,todo,date,managerName);
            }
        });
    }
    //삭제
    public void delete(Long id) {
        String sql="DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql,id);
    }
    //수정
    public void updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        String sql="UPDATE schedule SET name = ?, todo = ? , update_at=Now() WHERE id = ?";
        jdbcTemplate.update(sql,scheduleRequestDto.getName(),scheduleRequestDto.getTodo(),id);
    }
    public ScheduleResponseDto find(Long id) {
        String sql="SELECT * FROM schedule WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("todo"),
                        rs.getString("date"),
                        rs.getString("manager_name")
                );
            }
        },id);
    }
}
