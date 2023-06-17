package com.example.service;

import com.example.model.HomeData;
import com.example.model.RunnerDailyRecord;
import com.example.model.RunningData;
import com.example.repo.RunnerDailyRecordRepo;
import com.example.utils.GetDate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.stream.Collectors;

@Service
public class RunnerDailyRecordService {

    private final RunnerDailyRecordRepo runnerDailyRecordRepo;

    @Autowired
    public RunnerDailyRecordService(RunnerDailyRecordRepo runnerDailyRecordRepo) {
        this.runnerDailyRecordRepo = runnerDailyRecordRepo;
    }

    public List<HomeData> getHomeData(String userId){
        List<HomeData> homeDataList = new ArrayList<>();
        java.sql.Date curDate = new GetDate().getToday();
        java.sql.Date yesDate = new GetDate().getYesterday();
        List<RunnerDailyRecord> todayRecords = runnerDailyRecordRepo.getRecordByDay(userId,curDate);
        List<RunnerDailyRecord>  yesterdayRecords = runnerDailyRecordRepo.getRecordByDay(userId,yesDate);
        //
        HomeData mileData = new HomeData();
        mileData.setSubTitle("里程");
        mileData.setNowValue(todayRecords.stream().collect(Collectors.summarizingDouble(RunnerDailyRecord::getMile)));
        mileData.setLastValue(yesterdayRecords.stream().collect(Collectors.summarizingDouble(RunnerDailyRecord::getMile)));
        mileData.setUnit("公里");
        //
        HomeData minData = new HomeData();
        minData.setSubTitle("运动时间");
        Long runtime1 = 0L;
        for (RunnerDailyRecord todayRecord : todayRecords) {
            runtime1 += todayRecord.getDuration();
        }
        java.util.Date date1 = new java.util.Date(runtime1 * 1000);
        java.sql.Time sqlTime1 = new java.sql.Time(date1.getTime());
        Long runtime2 = 0L;
        for (RunnerDailyRecord yesterdayRecord : yesterdayRecords) {
            runtime2 += yesterdayRecord.getDuration();
        }
        java.util.Date date2 = new java.util.Date(runtime2 * 1000);
        java.sql.Time sqlTime2 = new java.sql.Time(date2.getTime());
        minData.setNowValue(sqlTime1);
        minData.setLastValue(sqlTime2);
        minData.setUnit("分钟");
        //
        HomeData stepData = new HomeData();
        stepData.setSubTitle("步频");
        mileData.setNowValue(todayRecords.stream().collect(Collectors.summarizingInt(RunnerDailyRecord::getStep)));
        mileData.setLastValue(yesterdayRecords.stream().collect(Collectors.summarizingInt(RunnerDailyRecord::getStep)));
        stepData.setUnit("步");
        //
        HomeData calorieData = new HomeData();
        calorieData.setSubTitle("消耗");
        mileData.setNowValue(todayRecords.stream().collect(Collectors.summarizingDouble(RunnerDailyRecord::getCalorie)));
        mileData.setLastValue(yesterdayRecords.stream().collect(Collectors.summarizingDouble(RunnerDailyRecord::getCalorie)));
        calorieData.setUnit("消耗");
        //
        homeDataList.add(mileData);
        homeDataList.add(minData);
        homeDataList.add(stepData);
        homeDataList.add(calorieData);
        return homeDataList;
    }

    public boolean uploadRunningData(String userId, Date date, RunningData runningData){
        RunnerDailyRecord newRecord = new RunnerDailyRecord();
        BeanUtils.copyProperties(runningData,newRecord);
        newRecord.setDate(date);
        newRecord.setUserId(userId);
        runnerDailyRecordRepo.save(newRecord);

        return true;
    }

    public List<RunningData> getRecordFromTo(String userId, Date from_date, Date to_date){
        List<RunnerDailyRecord> records = runnerDailyRecordRepo.getRecordFromTo(userId,from_date,to_date);
        List<RunningData> dataList = new ArrayList<>();
        BeanUtils.copyProperties(records,dataList);
        return dataList;
    }
}
