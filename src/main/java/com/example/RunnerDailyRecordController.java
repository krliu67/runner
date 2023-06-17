package com.example;

import com.example.model.RunningData;
import com.example.service.RunnerDailyRecordService;
import com.example.utils.GetDate;
import com.example.utils.ReturnData;
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
    public ReturnData getHome(@PathVariable("userId") String  userId){
        return new ReturnData(200,runnerDailyRecordService.getHomeData(userId),"success",2);
    }

    @PostMapping("/upload")
    @ApiOperation("上传用户的跑步数据")
    public ReturnData uploadRunningData(@PathVariable("userId") String userId, @RequestBody RunningData runningData){
        if (runnerDailyRecordService.uploadRunningData(userId,new GetDate().getToday(),runningData)){
            return new ReturnData(200,runningData,"success",1);
        }
        return new ReturnData(500,"fail","fail",1);
    }

    @PostMapping("/getRecordFromTo")
    @ApiOperation("获取当前⽤⼾的某⼀时段的运动记录")
    public ReturnData getRecordFromTo(@PathVariable("userId") String userId, @PathVariable("from_date") Date from_date, @PathVariable("to_date") Date to_date){
        List<RunningData> dataList = runnerDailyRecordService.getRecordFromTo(userId, from_date, to_date);
        return new ReturnData(200,dataList,"success",dataList.size());
    }

}
