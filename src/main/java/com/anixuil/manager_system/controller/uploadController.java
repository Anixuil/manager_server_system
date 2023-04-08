package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.entity.*;
import com.anixuil.manager_system.service.*;
import com.anixuil.manager_system.utils.ImportUtil;
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
    @Autowired
    UserTableService UserTableService;
    @Autowired
    CandidateTableService candidateTableService;
    @Autowired
    ClassTableService classTableService;
    @Autowired
    ExamClassTableService examClassTableService;
    @Autowired
    MajorTableService majorTableService;
    @Autowired
    StudentTableService studentTableService;
    @Autowired
    TeacherTableService teacherTableService;

    //excel表格导入
    @PostMapping("importExcel")
    public Rest importExcel(@RequestBody MultipartFile file,String className){
        String msg = "导入excel表格";
        try{
            if(file != null){
                switch (className){
//                    case "DepartTable":
//                        //调用ImportUtil工具类导入方法
//                        List<DepartTable> departTableList = new ImportUtil<DepartTable>().importExcel(file,className);
//                        departTableService.saveBatch(departTableList);
//                        break;
                    case "UserTable":
                        List<UserTable> userTableList = new ImportUtil<UserTable>().importExcel(file,className);

//                        UserTableService.saveBatch(userTableList);
                        break;
//                    case "CandidateTable":
//                        List<CandidateTable> candidateTableList = new ImportUtil<CandidateTable>().importExcel(file,className);
//                        candidateTableService.saveBatch(candidateTableList);
//                        break;
//                    case "ClassTable":
//                        List<ClassTable> classTableList = new ImportUtil<ClassTable>().importExcel(file,className);
//                        classTableService.saveBatch(classTableList);
//                        break;
//                    case "ExamClassTable":
//                        List<ExamClassTable> examClassTableList = new ImportUtil<ExamClassTable>().importExcel(file,className);
//                        examClassTableService.saveBatch(examClassTableList);
//                        break;
//                    case "MajorTable":
//                        List<MajorTable> majorTableList = new ImportUtil<MajorTable>().importExcel(file,className);
//                        majorTableService.saveBatch(majorTableList);
//                        break;
//                    case "StudentTable":
//                        List<StudentTable> studentTableList = new ImportUtil<StudentTable>().importExcel(file,className);
//                        studentTableService.saveBatch(studentTableList);
//                        break;
//                    case "TeacherTable":
//                        List<TeacherTable> teacherTableList = new ImportUtil<TeacherTable>().importExcel(file,className);
//                        teacherTableService.saveBatch(teacherTableList);
//                        break;
                }
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
