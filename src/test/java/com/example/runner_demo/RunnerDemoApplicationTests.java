package com.example.runner_demo;

import com.example.repo.UserRepo;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.vendor.Database;

import javax.sql.DataSource;
import java.sql.SQLException;

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
    }

    @Test
    void test() throws SQLException {
        System.out.println("数据库连接情况：" + dataSource.getConnection());
        System.out.println("-- 测试User --");
        System.out.println("result:" + userRepo.findAll());

    }
}
