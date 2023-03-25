package com.anixuil.manager_system.utils;

import cn.hutool.core.date.DateTime;

import java.sql.Timestamp;

public class Datetime {
    public static Timestamp getTimestamp(){
        return DateTime.now().toTimestamp();
    }
}
