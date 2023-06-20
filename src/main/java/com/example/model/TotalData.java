package com.example.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class TotalData implements Serializable {
    private Double totalDistance;
    private Integer totalDuration;
    private Double totalConsume;

    public TotalData(Double totalDistance, Integer totalDuration, Double totalConsume) {
        this.totalDistance = totalDistance;
        this.totalDuration = totalDuration;
        this.totalConsume = totalConsume;
    }
}