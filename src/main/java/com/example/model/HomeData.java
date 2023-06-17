package com.example.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

@Data
public class HomeData implements Serializable {

    @ApiModelProperty("標題")
    private String SubTitle;

    @ApiModelProperty("今日數據")
    private Object NowValue;

    @ApiModelProperty("昨日數據")
    private Object LastValue;

    @ApiModelProperty("單位")
    private String Unit;
}
