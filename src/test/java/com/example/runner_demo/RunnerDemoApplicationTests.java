package com.example.runner_demo;

import com.example.model.User;
import com.example.repo.UserRepo;
import com.example.service.UserService;
import com.example.utils.GenCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.vendor.Database;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

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
}
