package com.example.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DayDataDto implements Serializable {
    // mile, duration, step, calorie
    private Double mile;
    private Long duration;
    private Integer step;
    private Double calorie;

    public DayDataDto(Double mile, Long duration, Integer step, Double calorie) {
        this.mile = mile;
        this.duration = duration;
        this.step = step;
        this.calorie = calorie;
    }
}
