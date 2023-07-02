package com.example.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
public class TotalData implements Serializable {
    private Double totalDistance;
    private Integer totalDuration;
    private Double totalConsume;

}
