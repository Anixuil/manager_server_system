package com.anixuil.manager_system.utils;

import com.alibaba.excel.EasyExcel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public class ImportUtil<T> {

    //枚举类 用来存放所有实体类全类名
    enum EntityName{
        DepartTable("com.anixuil.manager_system.entity.DepartTable"),
        UserTable("com.anixuil.manager_system.entity.UserTable"),
        CandidateTable("com.anixuil.manager_system.entity.CandidateTable"),
        ClassTable("com.anixuil.manager_system.entity.ClassTable"),
        ExamClassTable("com.anixuil.manager_system.entity.ExamClassTable"),
        MajorTable("com.anixuil.manager_system.entity.MajorTable"),
        StudentTable("com.anixuil.manager_system.entity.StudentTable"),
        TeacherTable("com.anixuil.manager_system.entity.TeacherTable");

        private String value;
        EntityName(String value){
            this.value = value;
        }
        public String getValue(){
            return value;
        }

    }

    //导入方法
    public List<T> importExcel(MultipartFile file, String className) throws ClassNotFoundException, IOException {
        List<T> importDatas;
        Class<T> clazz = (Class<T>) Class.forName(EntityName.valueOf(className).getValue());
        CustomListener<T> customListener = new CustomListener<>(clazz);
        //调用easyexcel工具类
        if(className.equals("UserTable")){
            EasyExcel.read(file.getInputStream(), clazz, customListener)
//                .head(Class.forName(EntityName.valueOf(className).getValue()))
                    .sheet()
                    .doRead();
        }

        importDatas = customListener.getDataList();
        return importDatas;
    }
}
