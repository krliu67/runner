package com.example.service;

import com.example.model.HomeData;
import com.example.model.RunnerDailyRecord;
import com.example.dto.RunnerRankDto;
import com.example.model.RunningData;
import com.example.model.TotalData;
import com.example.repo.RunnerDailyRecordRepo;
import com.example.utils.GetDate;
import com.example.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        String todayRecords = runnerDailyRecordRepo.getRecordByDay(userId,curDate);
        String yesterdayRecords = runnerDailyRecordRepo.getRecordByDay(userId,yesDate);
        System.out.println(todayRecords);
        System.out.println(yesterdayRecords);
        HomeData mileData = new HomeData();
        mileData.setSubTitle("里程");
        mileData.setUnit("公里");
        HomeData minData = new HomeData();
        minData.setSubTitle("运动时间");
        minData.setUnit("分钟");
        HomeData stepData = new HomeData();
        stepData.setSubTitle("步频");
        stepData.setUnit("步");
        HomeData calorieData = new HomeData();
        calorieData.setSubTitle("消耗");
        calorieData.setUnit("消耗");
        //
        String[] todayStrs = todayRecords.split(",");
        String[] yesdayStrs = yesterdayRecords.split(",");
        if (todayStrs[0].equals("null")){
            log.info("今天没有运动");
        }else{
            mileData.setNowValue(Double.parseDouble(todayStrs[0]));
            long runtime1 = 0L;
            runtime1 += Long.parseLong(todayStrs[1]);
            String s1 = new TimeUtils().second2Time(runtime1);
            minData.setNowValue(s1);
            stepData.setNowValue(Integer.parseInt(todayStrs[2]));
            calorieData.setNowValue(Double.parseDouble(todayStrs[3]));
        }

        if (yesdayStrs[0].equals("null")){
            log.info("昨天没有运动");
        }else{
            mileData.setNowValue(Double.parseDouble(yesdayStrs[0]));
            long runtime2 = 0L;
            runtime2 += Long.parseLong(yesdayStrs[1]);
            String s2 = new TimeUtils().second2Time(runtime2);
            minData.setNowValue(s2);
            stepData.setNowValue(Integer.parseInt(yesdayStrs[2]));
            calorieData.setNowValue(Double.parseDouble(yesdayStrs[3]));
        }
        homeDataList.add(mileData);
        homeDataList.add(minData);
        homeDataList.add(stepData);
        homeDataList.add(calorieData);
        return homeDataList;
    }

    public TotalData getTotalData(String userId){
        // TODO 先访问redis，如果redis中有数据，则不需要查询mysql
        String objects = runnerDailyRecordRepo.getTotalData(userId);
        String[] strs = objects.split(",");
        // System.out.println(objects); // 861.6,3333,1474.7
        return new TotalData(Double.parseDouble(strs[0]), Integer.parseInt(strs[1]), Double.parseDouble(strs[2]));
    }
    public boolean uploadRunningData(String userId, Date date, RunningData runningData){
        RunnerDailyRecord newRecord = new RunnerDailyRecord();
        BeanUtils.copyProperties(runningData,newRecord);
        newRecord.setDate(date);
        newRecord.setUserId(userId);
        runnerDailyRecordRepo.save(newRecord);
        // 上传跑步完成之后数据，上传完成之后需要同时更新⽤⼾的跑步总⾥程、总时间、总消耗
        // 设置删除redis
        return true;
    }

    public List<RunningData> getRecordFromTo(String userId, Date from_date, Date to_date){
        // TODO 序列化Json问题
        List<RunnerDailyRecord> records = runnerDailyRecordRepo.getRecordFromTo(userId,from_date,to_date);
        List<RunningData> dataList = new ArrayList<>();
        BeanUtils.copyProperties(records,dataList);
        return dataList;
    }

    public List<RunnerRankDto> getAllRankFromTo(Date from_date, Date to_date,Integer start, Integer row){

        List<Object[]> list = runnerDailyRecordRepo.getAllRankFromTo(from_date, to_date,start,row);
        List<RunnerRankDto> runnerRankDtos = list.stream().map(rank -> {
            return new RunnerRankDto((String) rank[0], (Double) rank[1], (String) rank[2], (String) rank[3], (String) rank[4]);
        }).collect(Collectors.toList());
        return runnerRankDtos;
    }
}
