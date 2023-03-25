package com.anixuil.manager_system.utils;

import java.util.UUID;

public class Uuid {
    public static String getUuid(){
        String id = UUID.randomUUID().toString();
        return id.replaceAll("-","");
    }

    //测试
    public static void main(String[] args) {
        System.out.println(getUuid());
    }
}
