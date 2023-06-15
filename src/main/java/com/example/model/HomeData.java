package com.example.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

@Data
public class HomeData implements Serializable {
    private String SubTitle;
    private Object NowValue;
    private Object LastValue;
    private String Unit;
}
