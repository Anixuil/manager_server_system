package com.anixuil.manager_system.utils;

import cn.hutool.core.date.DateTime;

import java.sql.Timestamp;

public class Datetime {
    public static Timestamp getTimestamp(){
        return DateTime.now().toTimestamp();
    }

    public static String format(Timestamp timestamp){
        return DateTime.of(timestamp).toString("yyyy-MM-dd HH:mm:ss");
    }

    public static String format(Timestamp timestamp,String pattern){
        return DateTime.of(timestamp).toString(pattern);
    }
}
