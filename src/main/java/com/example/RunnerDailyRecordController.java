package com.example;

import com.example.dto.RunnerRankDto;
import com.example.model.RunningData;
import com.example.service.RunnerDailyRecordService;
import com.example.utils.GetDate;
import com.example.common.ReturnData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/rdr")
@Slf4j
@Api(tags = "RunnerDailyRecord接口")
public class RunnerDailyRecordController {

    private final RunnerDailyRecordService runnerDailyRecordService;

    public RunnerDailyRecordController(RunnerDailyRecordService runnerDailyRecordService) {
        this.runnerDailyRecordService = runnerDailyRecordService;
    }

    // 首页数据
    @PostMapping("/home")
    @ApiOperation("获取用户首页数据")
    public ReturnData getHome(String  userId){
        return new ReturnData(200,runnerDailyRecordService.getHomeData(userId),"success",2);
    }

    @PostMapping("/upload")
    @ApiOperation("上传用户的跑步数据")
    public ReturnData uploadRunningData(String userId, @RequestBody RunningData runningData){
        runnerDailyRecordService.uploadRunningData(userId,new GetDate().getToday(),runningData);
        return new ReturnData(200,runningData,"success",1);

    }

    @PostMapping("/getRecordFromTo")
    @ApiOperation("获取当前⽤⼾的某⼀时段的运动记录")
    public ReturnData getRecordFromTo(String userId,Date from_date, Date to_date){
        List<RunningData> dataList = runnerDailyRecordService.getRecordFromTo(userId, from_date, to_date);
        return new ReturnData(200,dataList,"success",dataList.size());
    }

    @GetMapping("/rank")
    @ApiOperation("获取某⼀时段的用戶排名 + 分页")
    /***
     * start为开始位置，row为要检索的行数
     */
    public ReturnData getAllRankFromTo(Date from_date,Date to_date,Integer start, Integer row){
        List<RunnerRankDto> runnerRanks = runnerDailyRecordService.getAllRankFromTo(from_date, to_date,start,row);
        return new ReturnData(200,runnerRanks,"success",runnerRanks.size());
    }

    @GetMapping("/total")
    @ApiOperation("获取当前一个⽤⼾的跑步总距离，总时⻓，总共消耗的卡路⾥")
    public ReturnData getTotalData(String userId){
        return new ReturnData(200,runnerDailyRecordService.getTotalData(userId),"success",3);
    }

}
