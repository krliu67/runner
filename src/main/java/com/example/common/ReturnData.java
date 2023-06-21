package com.example.common;

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

    public ReturnData() {

    }
    // 成功返回  携带内容
    public static ReturnData success(Object object) {
        ReturnData returnData = new ReturnData();
        returnData.setData(object);
        returnData.setCode(200);
        returnData.setMsg("success");
        return returnData;
    }
    //成功返回  不携带内容
    public static ReturnData success() {
        return success(null);
    }
    //错误返回 携带code和msg
    public static ReturnData error(Integer code, String msg) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(code);
        returnData.setMsg(msg);
        return returnData;
    }
}
