package com.example.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class BeanMapUtilByJson {
    /**
     * 对象转Map
     * @param object
     * @return
     */
    public static Map beanToMap(Object object){
        return JSONObject.parseObject(JSON.toJSONString(object),Map.class);
    }

    /**
     * map转对象
     * @param map
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> Object mapToBean(Map map, Class<T> beanClass){
        return JSONObject.parseObject(JSON.toJSONString(map),beanClass);
    }
}
