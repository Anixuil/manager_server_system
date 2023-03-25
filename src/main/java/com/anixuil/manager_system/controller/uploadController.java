package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.entity.Rest;
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
