package com.example.runner_demo;

import com.example.model.User;
import com.example.repo.UserRepo;
import com.example.service.UserService;
import com.example.utils.GenCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@SpringBootTest
class RunnerDemoApplicationTests {
    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    DataSource dataSource;


    @Test
    void contextLoads() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        System.out.println(uuid);
    }

    @Test
    void testDBConn() throws SQLException {
        System.out.println("数据库连接情况：" + dataSource.getConnection());
        System.out.println("-- 测试User --");
        System.out.println("result:" + userRepo.findAll());

    }

    @Test
    public void testTime(){
        java.sql.Date curDate = new java.sql.Date(new Date().getTime());
        System.out.println(curDate.toString());
        Date dBefore = new Date();
        Date dNow = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间
        java.sql.Date yesterDate = new java.sql.Date(dBefore.getTime());
        System.out.println(curDate.toString());
        System.out.println(yesterDate.toString());
    }

    @Test
    void testUser() {
        System.out.println("-- 测试addUser --");
        User newUser = new User();
        String newUserId = new GenCode().GenUserId();
        System.out.println(newUserId);
        newUser.setUserId(newUserId);
        newUser.setUsername("testtest");
        newUser.setPassword("testtest");
        String newInviteCode= new GenCode().GenInviteCode();
        System.out.println(newInviteCode);
        newUser.setInviteCode(newInviteCode);
        newUser.setPhone("1100000222");
        System.out.println(userService.addUser(newUser));

        System.out.println(userService.getAllUsers());
//        System.out.println("-- 测试deleteUser --");
//        userService.deleteEmployee(newUserId);
//        System.out.println("-- 测试findUser --");
//        System.out.println(userService.findUserByUserId(newUserId));
        System.out.println("-- 测试updateUser --");
        User updateUser = userService.findUserByUserId(newUserId);
        updateUser.setPhone("1111111111");
        userService.updateUser(updateUser);
        System.out.println(userService.findUserByUserId(newUserId));

        System.out.println("-- 测试findUsers --");
        List<String> list = new ArrayList<>();
        list.add(newUserId);
        list.add("uEMxFR5G");
        System.out.println(userService.findUserByUserIds(list));
    }

    @Test
    void t(){
        double result=1.0-0.9;
        System.out.println(result);
    }
}
