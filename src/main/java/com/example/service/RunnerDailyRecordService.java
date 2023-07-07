package com.example.service;

import com.example.config.RabbitConfig;
import com.example.exception.DiyException;
import com.example.exception.ErrorType;
import com.example.model.HomeData;
import com.example.model.RunnerDailyRecord;
import com.example.dto.RunnerRankDto;
import com.example.model.RunningData;
import com.example.model.TotalData;
import com.example.repo.RunnerDailyRecordRepo;
import com.example.utils.BeanMapUtilByJson;
import com.example.utils.GetDate;
import com.example.utils.TimeUtils;
import com.example.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Array;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RunnerDailyRecordService {

    private final RunnerDailyRecordRepo runnerDailyRecordRepo;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    RabbitTemplate rabbitTemplate;

    @Autowired
    public RunnerDailyRecordService(RunnerDailyRecordRepo runnerDailyRecordRepo) {
        this.runnerDailyRecordRepo = runnerDailyRecordRepo;
    }


    public List<HomeData> getHomeData(String userId) {
//        rabbitTemplate.convertAndSend(RabbitConfig.RUNNER_EXCHANGE,RabbitConfig.RUNNER_QUEUE,userId);
        String query_param = "HomeData-" + userId;
        Object cache_data = redisUtils.get(query_param);
        if (cache_data != null) {
            // 如果 Redis 中存在数据，直接返回
            List<HomeData> homeDataList = (List<HomeData>) cache_data;
            System.out.println("Redis 中存在数据，直接返回");
            return homeDataList;
        } else {
            //  如果 Redis 中不存在数据，则从 MySQL 查询数据
            //  进行 MySQL 查询的逻辑
            //  获取到数据后，将数据存储到 Redis
            List<HomeData> homeDataList = new ArrayList<>();
            java.sql.Date curDate = new GetDate().getToday();
            java.sql.Date yesDate = new GetDate().getYesterday();
            String todayRecords = runnerDailyRecordRepo.getRecordByDay(userId, curDate);
            String yesterdayRecords = runnerDailyRecordRepo.getRecordByDay(userId, yesDate);
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
            String[] todayStrs = todayRecords.split(",");
            String[] yesdayStrs = yesterdayRecords.split(",");
            if (todayStrs[0].equals("null")) {
                log.info("今天没有运动");
                throw new DiyException(ErrorType.TODAY_NO_RUN);
            } else {
                mileData.setNowValue(Double.parseDouble(todayStrs[0]));
                long runtime1 = 0L;
                runtime1 += Long.parseLong(todayStrs[1]);
                String s1 = new TimeUtils().second2Time(runtime1);
                minData.setNowValue(s1);
                stepData.setNowValue(Integer.parseInt(todayStrs[2]));
                calorieData.setNowValue(Double.parseDouble(todayStrs[3]));
            }

            if (yesdayStrs[0].equals("null")) {
                log.info("昨天没有运动");
                throw new DiyException(ErrorType.YESTERDAY_NO_RUN);
            } else {
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
                redisUtils.set(query_param, homeDataList, "3600");
                System.out.println("从 MySQL 查询数据");
                return homeDataList;
            }
        }

    public List<TotalData> getTotalData(String userId) {
        String query_param = "TotalData-" + userId;
        Object cache_data = redisUtils.get(query_param);
        if (cache_data != null) {
            // 如果 Redis 中存在数据，直接返回
            List<TotalData> totalDataList = (List<TotalData>) cache_data;
            System.out.println("Redis 中存在数据，直接返回");
            return totalDataList;
        } else {
            //  如果 Redis 中不存在数据，则从 MySQL 查询数据
            //  进行 MySQL 查询的逻辑
            //  获取到数据后，将数据存储到 Redis
            List<Map<String,Object>> records  = runnerDailyRecordRepo.getTotalData(userId);
            List<TotalData> totalDataList = records.stream().map( data -> {
                return (TotalData) BeanMapUtilByJson.mapToBean(data,TotalData.class);
            }).collect(Collectors.toList());
            System.out.println(totalDataList);
            redisUtils.set(query_param,totalDataList,"3600");
            System.out.println("从 MySQL 查询数据");
            return totalDataList;
        }
    }

    public void uploadRunningData(String userId, Date date, RunningData runningData){
        RunnerDailyRecord newRecord = new RunnerDailyRecord();
        BeanUtils.copyProperties(runningData,newRecord);
        newRecord.setDate(date);
        newRecord.setUserId(userId);
        // rabbitmq异步执行
        log.info("传递uploadRunningData的消息，来自"+userId);
        rabbitTemplate.convertAndSend(RabbitConfig.RUNNER_EXCHANGE_UploadRunningData,RabbitConfig.RUNNER_QUEUE_UploadRunningData,newRecord);
    }
    @RabbitListener(queues = RabbitConfig.RUNNER_QUEUE_UploadRunningData)
    @RabbitHandler
    public void uploadRunningData1(RunnerDailyRecord newRecord){
        log.info("收到uploadRunningData的消息，来自"+newRecord.getUserId());
        runnerDailyRecordRepo.save(newRecord);
        // 上传跑步完成之后数据，上传完成之后需要同时更新⽤⼾的跑步总⾥程、总时间、总消耗
        // 设置删除redis
        List<String> query_params = new ArrayList<>();
        String query_param_total = "TotalData-" + userId;
        String query_param_home = "HomeData-" + userId;
        query_params.add(query_param_total);
        query_params.add(query_param_home);
        for (String query : query_params) {
            redisUtils.delete(query);
        }
        return true;
    }

    public List<RunningData> getRecordFromTo(String userId, Date from_date, Date to_date){
        List<Map<String,Object>> records = runnerDailyRecordRepo.getRecordFromTo(userId,from_date,to_date);
        List<RunningData> runningDataList = records.stream().map( data -> {
            return (RunningData) BeanMapUtilByJson.mapToBean(data,RunningData.class);
        }).collect(Collectors.toList());
        return runningDataList;
    }

    public List<RunnerRankDto> getAllRankFromTo(Date from_date, Date to_date,Integer start, Integer row){
        List<Object[]> list = runnerDailyRecordRepo.getAllRankFromTo(from_date, to_date,start,row);
        List<RunnerRankDto> runnerRankDtos = list.stream().map(rank -> {
            return new RunnerRankDto((String) rank[0], (Double) rank[1], (String) rank[2], (String) rank[3], (String) rank[4]);
        }).collect(Collectors.toList());
        return runnerRankDtos;
    }
}
