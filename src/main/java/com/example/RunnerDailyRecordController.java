package com.example;

import com.example.service.RunnerDailyRecordService;
import com.example.utils.ReturnData;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ReturnData getHome(@PathVariable("userId") String  userId){
        return new ReturnData(200,runnerDailyRecordService.getHomeData(userId),"success",2);
    }

}
