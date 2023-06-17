package com.example.repo;

import com.example.model.RunnerDailyRecord;
import com.example.model.RunningData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface RunnerDailyRecordRepo extends JpaRepository<RunnerDailyRecord,Long> {
    @Query(value = "select mile, minute, step, calorie from runner.runner_daily_record where user_id = ?1 and date = ?2")
    // 根据用户id和日期查询对应数据
    public List<RunnerDailyRecord> getRecordByDay(String userID, Date date);

    @Query(value = "select path, duration, mile, calorie,average_speed,average_step_frepuency,max_speed,max_step_frequency" +
            "date,step_frequency,speed,weather,temperature from runner.runner_daily_record where user_id = ?1 and date between ?2 and ?3")
    public List<RunnerDailyRecord> getRecordFromTo(String userID, Date from_date,Date to_date);
}
