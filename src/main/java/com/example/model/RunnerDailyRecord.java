package com.example.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Data
@Table(name = "runner_daily_record")
@Entity
public class RunnerDailyRecord implements Serializable {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false)
    private Long id;

    @Column(nullable = false,updatable = false)
    private String userId;

    // TODO 参考接口文档的 获取⾸⻚信息 处
    private Date date;

    private Float mile;

    private Time minute;

    private Integer step;

    private Float calorie;
}
