package com.example.repo;

import com.example.model.RunnerDailyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;

public interface RunnerDailyRecordRepo extends JpaRepository<RunnerDailyRecord,Long> {
    @Query(value = "select mile, minute, step, calorie from runner.runner_daily_record where user_id = ?1 and date = ?2")
    // 根据用户id和日期查询对应数据
    public RunnerDailyRecord getRecordByDay(String userID,Date date);
}
