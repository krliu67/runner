package com.example.utils;

import lombok.Data;

import java.io.Serializable;
@Data
public class ReturnData implements Serializable {
    private Integer code;

    private Object data;

    private String msg;

    private Integer count;

    public ReturnData(Integer code, Object data, String msg, Integer count) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.count = count;
    }
}
