package com.example.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RunnerRankDto implements Serializable {

    private String userId;

    private Double miles;

    private String rank;

    private String avatarUrl;

    private String signature;


    public RunnerRankDto(String userId, Double miles, String rank, String avatarUrl, String signature) {
        this.userId = userId;
        this.miles = miles;
        this.rank = rank;
        this.avatarUrl = avatarUrl;
        this.signature = signature;
    }
}
