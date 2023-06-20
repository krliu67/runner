package com.example.utils;

import java.util.Calendar;
import java.util.Date;

public class GetDate {

    public java.sql.Date getToday(){
        java.sql.Date curDate = new java.sql.Date(new Date().getTime());
        return curDate;
    }

    public java.sql.Date getYesterday(){
        java.sql.Date curDate = new java.sql.Date(new Date().getTime());
        //System.out.println(curDate.toString());
        Date dBefore = new Date();
        Date dNow = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间
        java.sql.Date yesterDate = new java.sql.Date(dBefore.getTime());
        //System.out.println(curDate.toString());
        //System.out.println(yesterDate.toString());
        return yesterDate;
    }

    public java.sql.Date getAnoth(){
        java.sql.Date curDate = new java.sql.Date(new Date().getTime());
        //System.out.println(curDate.toString());
        Date dBefore = new Date();
        Date dNow = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -18);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间
        java.sql.Date yesterDate = new java.sql.Date(dBefore.getTime());
        //System.out.println(curDate.toString());
        //System.out.println(yesterDate.toString());
        return yesterDate;
    }
}
