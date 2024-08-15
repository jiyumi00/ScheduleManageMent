package com.sparta.schedulemanagement.entity;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ScheduleRepository {
    //임시 저장소
    private static final Map<Long,Schedule> scheduleList=new HashMap<>();
    private static long sequence=0L;

    public void save(Schedule schedule){
        schedule.setId(++sequence);
        scheduleList.put(schedule.getId(),schedule);
    }

    public Schedule findById(Long id){
        return scheduleList.get(id);
    }

    public List<Schedule> findAll(){
        return new ArrayList<>(scheduleList.values());
    }

}
