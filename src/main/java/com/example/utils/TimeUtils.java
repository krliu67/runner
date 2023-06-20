package com.example.utils;

public class TimeUtils {


    /**
     * @param second 秒
     * @description: 秒转换为时分秒 HH:mm:ss 格式 仅当小时数大于0时 展示HH
     * @return: {@link String}
     * @author: pzzhao
     * @date: 2022-05-08 13:55:17
     */
    public String second2Time(Long second) {
        if (second == null || second < 0) {
            return "00:00";
        }

        long h = second / 3600;
        long m = (second % 3600) / 60;
        long s = second % 60;
//        String str = "";
//        if (h > 0) {
//            str = (h < 10 ? ("0" + h) : h) + ":";
//        }
//        str += (m < 10 ? ("0" + m) : m) + ":";
//        str += (s < 10 ? ("0" + s) : s);
//        System.out.println("test:" + " "+h+" "+m+" "+ s);
//        return str;
         String str = h+":"+m+":"+s;
        return str;
    }
}
