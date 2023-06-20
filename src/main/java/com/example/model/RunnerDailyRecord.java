package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import springfox.documentation.spring.web.json.Json;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "runner_daily_record")
@Entity
@TypeDef(name="json",typeClass = JsonStringType.class)
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler", "fieldHandler"})
public class RunnerDailyRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false)
    @ApiModelProperty("主鍵id")
    private Long id;

    @Column(nullable = false,updatable = false)
    @ApiModelProperty("用戶id")
    private String userId;

    // TODO 参考接口文档的 获取⾸⻚信息 + 上传跑步数据 处

    @ApiModelProperty("記錄日期")
    private Date date;

    @ApiModelProperty("跑步公里")
    private Double mile;


    @ApiModelProperty("跑步步数")
    private Integer step;

    @ApiModelProperty("跑步卡路里")
    private Double calorie;

    @JsonIgnore
    @Type(type="json")
    @ApiModelProperty("跑步过程中的点集合")
    private List<String> path;

    @ApiModelProperty("跑步持续的时⻓（s）")
    private Long duration;

    @ApiModelProperty("平均速度（m/s）")
    private Double averageSpeed;

    @ApiModelProperty("平均步频（步/min）")
    private Integer averageStepFrequency;

    @ApiModelProperty("最⼤速度（m/s）")
    private Double maxSpeed;

    @ApiModelProperty("最⼤步频（步/min）")
    private Integer maxStepFrequency;

    @JsonIgnore
    @Type(type="json")
    @ApiModelProperty("步频分布")
    private List<String> stepFrequency;

    @JsonIgnore
    @Type(type="json")
    @ApiModelProperty("速度分布")
    private List<String> speed;

    @ApiModelProperty("跑步时的天⽓")
    // 跑步时的天⽓[0, 1, 2, 3, 4] => 雷阵⾬ 晴 雪 阴 ⾬
    private Integer weather;

    @ApiModelProperty("氣溫")
    private Integer temperature;
}
