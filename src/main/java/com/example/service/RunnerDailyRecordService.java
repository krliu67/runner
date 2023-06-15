package com.example.service;

import com.example.model.HomeData;
import com.example.model.RunnerDailyRecord;
import com.example.repo.RunnerDailyRecordRepo;
import com.example.utils.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RunnerDailyRecordService {

    private final RunnerDailyRecordRepo runnerDailyRecordRepo;

    @Autowired
    public RunnerDailyRecordService(RunnerDailyRecordRepo runnerDailyRecordRepo) {
        this.runnerDailyRecordRepo = runnerDailyRecordRepo;
    }

    public List<HomeData> getHomeData(String userId){
        List<HomeData> homeDataList = new ArrayList<>();
        java.sql.Date curDate = new GetDate().GetToday();
        java.sql.Date yesDate = new GetDate().GetYesterday();
        RunnerDailyRecord todayRecord = runnerDailyRecordRepo.getRecordByDay(userId,curDate);
        RunnerDailyRecord yesterdayRecord = runnerDailyRecordRepo.getRecordByDay(userId,yesDate);
        HomeData mileData = new HomeData();
        mileData.setSubTitle("里程");
        mileData.setNowValue(todayRecord.getMile());
        mileData.setLastValue(yesterdayRecord.getMile());
        mileData.setUnit("公里");
        HomeData minData = new HomeData();
        minData.setSubTitle("运动时间");
        minData.setNowValue(todayRecord.getMinute());
        minData.setLastValue(yesterdayRecord.getMinute());
        minData.setUnit("分钟");
        HomeData stepData = new HomeData();
        stepData.setSubTitle("步频");
        stepData.setNowValue(todayRecord.getStep());
        stepData.setLastValue(yesterdayRecord.getStep());
        stepData.setUnit("步");
        HomeData calorieData = new HomeData();
        calorieData.setSubTitle("消耗");
        calorieData.setNowValue(todayRecord.getCalorie());
        calorieData.setLastValue(yesterdayRecord.getCalorie());
        calorieData.setUnit("消耗");
        homeDataList.add(mileData);
        homeDataList.add(minData);
        homeDataList.add(stepData);
        homeDataList.add(calorieData);
        return homeDataList;
    }
}
