package com.example.repo;

import com.example.model.RunnerDailyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface RunnerDailyRecordRepo extends JpaRepository<RunnerDailyRecord,Long> {
    @Query(value = "select sum(mile), sum(duration), sum(step), sum(calorie) from runner.runner_daily_record where user_id = ?1 and date = ?2", nativeQuery = true)
//    @Query(value = "select * from runner.runner_daily_record where user_id = ?1 and date = ?2", nativeQuery = true)
    // 根据用户id和日期查询对应数据
    public String getRecordByDay(String userId, Date date);

//    @Query(value = "select id,user_id,path, duration, mile, calorie,average_speed,average_step_frequency,max_speed,max_step_frequency," +
//            "date,step_frequency,speed,weather,temperature from runner.runner_daily_record where user_id = ?1 and runner_daily_record.date between ?2 and ?3", nativeQuery = true)
    @Query(value = "select * from runner.runner_daily_record where user_id = ?1 and runner_daily_record.date between ?2 and ?3", nativeQuery = true)
    public List<RunnerDailyRecord> getRecordFromTo(String userId, Date from_date,Date to_date);

    @Query(value = "SELECT t.user_id, t.miles, t.rank, t.avatar_url, t.signature FROM (SELECT u.avatar_url, u.signature,u.user_id, u.miles, @rank \\:= @rank + 1, " +
            "@last_rank \\:= CASE  WHEN @last_score = u.miles  THEN @last_rank  WHEN @last_score \\:= u.miles THEN @rank END AS rank " +
            "FROM (SELECT t2.signature, t2.avatar_url, t2.username, t1.user_id, sum(t1.mile) miles \n" +
            "FROM \n" +
            "runner_daily_record t1, runner.`user` t2 where t1.user_id = t2.user_id and t1.date BETWEEN ?1 and ?2 GROUP BY t1.user_id ORDER BY miles DESC) u, " +
            "(SELECT @rank \\:= 0, @last_score \\:= NULL, @last_rank \\:= 0) r) t limit ?3 , ?4 ", nativeQuery = true)
    public List<Object[]> getAllRankFromTo(Date from_date, Date to_date, Integer start, Integer row);

    @Query( value = "select sum(mile) m, sum(duration) d, sum(calorie) c from runner.runner_daily_record where runner.runner_daily_record.user_id = ?1", nativeQuery = true)
    public String getTotalData(String userId);


}
