package com.anixuil.manager_system.controller;

import com.alibaba.excel.EasyExcel;
import com.anixuil.manager_system.entity.DepartTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.service.DepartTableService;
import com.anixuil.manager_system.utils.DepartListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("publicfile")
public class uploadController {

    @Autowired
    DepartTableService departTableService;

//    //枚举类 用来存放所有service类全类名
//    enum ServiceName{
//        departTableService("com.anixuil.manager_system.service.DepartTableService"),
//        majorTableService("com.anixuil.manager_system.service.MajorTableService"),
//        studentTableService("com.anixuil.manager_system.service.StudentTableService"),
//        teacherTableService("com.anixuil.manager_system.service.TeacherTableService"),
//        classTableService("com.anixuil.manager_system.service.ClassTableService"),
//        examScoreTableService("com.anixuil.manager_system.service.ExamScoreTableService"),
//        userTableService("com.anixuil.manager_system.service.UserTableService"),
//        candidateTableService("com.anixuil.manager_system.service.CandidateTableService");
//
//        private final String value;
//
//        ServiceName(String value){
//            this.value = value;
//        }
//
//        public String getValue(){
//            return value;
//        }
//    }

    //excel表格导入
    @PostMapping("importExcel")
    public Rest importExcel(@RequestBody MultipartFile file,String className) throws ClassNotFoundException {
        String msg = "导入excel表格";
        try{
            if(file != null){
                EasyExcel.read(file.getInputStream(), DepartTable.class,new DepartListener(departTableService)).sheet().doRead();
                return Rest.success(msg,"导入成功");
            }
            return Rest.fail(msg,"导入失败");
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //单个文件上传
//    @PostMapping("upload")
    @PostMapping("upload")
    public Rest upload(@RequestBody MultipartFile file){
        String msg = "上传文件";
        try{
            Rest result = saveFile(file,msg);
            return result;
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //多文件上传
    @PostMapping("multiUpload")
    public Rest multiUpload(@RequestBody MultipartFile[] files){
        String msg = "上传文件";
        List<Rest> result = new ArrayList<Rest>();
        for(MultipartFile file : files){
            result.add(saveFile(file,msg));
        }
        return Rest.success(msg,result);
    }



    private Rest saveFile(MultipartFile file,String msg){
        if(file.isEmpty()){
            return Rest.fail(msg,"未检测到文件");
        }
        String fileName = file.getOriginalFilename();   //获取上传文件原名称
        String filePath = "D:/code/java/public/";

        File temp = new File(filePath);
        if(!temp.exists()){
            temp.mkdirs();
        }

        File localFile = new File(filePath + fileName);
        try{
            file.transferTo(localFile); //把上传的文件保存到本地
            return Rest.success(msg,"http://localhost:8080/anixuil/public/" + fileName);
        }catch (IOException e){
            e.printStackTrace();
            return Rest.fail(msg,e);
        }
    }
}
