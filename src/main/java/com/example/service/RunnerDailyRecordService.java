package com.example.service;

import com.example.model.HomeData;
import com.example.model.RunnerDailyRecord;
import com.example.dto.RunnerRankDto;
import com.example.model.RunningData;
import com.example.model.TotalData;
import com.example.repo.RunnerDailyRecordRepo;
import com.example.utils.GetDate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        List<Object[]> todayRecords = runnerDailyRecordRepo.getRecordByDay(userId,curDate);
        List<Object[]> yesterdayRecords = runnerDailyRecordRepo.getRecordByDay(userId,yesDate);

        //
        HomeData mileData = new HomeData();
        mileData.setSubTitle("里程");
        mileData.setNowValue(Double.parseDouble(Arrays.toString(todayRecords.get(0))));
        mileData.setLastValue(Double.parseDouble(Arrays.toString(yesterdayRecords.get(0))));
        mileData.setUnit("公里");
        //
        HomeData minData = new HomeData();
        minData.setSubTitle("运动时间");
        Long runtime1 = 0L;
        runtime1 += Long.getLong(Arrays.toString(todayRecords.get(1)));
        java.util.Date date1 = new java.util.Date(runtime1 * 1000);
        java.sql.Time sqlTime1 = new java.sql.Time(date1.getTime());
        Long runtime2 = 0L;
        runtime2 += Long.getLong(Arrays.toString(yesterdayRecords.get(1)));
        java.util.Date date2 = new java.util.Date(runtime2 * 1000);
        java.sql.Time sqlTime2 = new java.sql.Time(date2.getTime());
        minData.setNowValue(sqlTime1);
        minData.setLastValue(sqlTime2);
        minData.setUnit("分钟");
        //
        HomeData stepData = new HomeData();
        stepData.setSubTitle("步频");
        mileData.setNowValue(Integer.parseInt(Arrays.toString(todayRecords.get(2))));
        mileData.setLastValue(Integer.parseInt(Arrays.toString(yesterdayRecords.get(2))));
        stepData.setUnit("步");
        //
        HomeData calorieData = new HomeData();
        calorieData.setSubTitle("消耗");
        mileData.setNowValue(Double.parseDouble(Arrays.toString(todayRecords.get(3))));
        mileData.setLastValue(Double.parseDouble(Arrays.toString(yesterdayRecords.get(3))));
        calorieData.setUnit("消耗");
        // TODO 序列化Json问题
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
