package com.anixuil.manager_system.controller;


import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.obs.services.exception.ObsException;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.obsService.HweiYunOBSService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("file")
public class HweiYunOBSController {
    @Resource
    private HweiYunOBSService hweiYunOBSService;

    // 上传
    @PostMapping("upload")
    public Rest save(@RequestParam(value = "file",required = false) MultipartFile file){
        String msg = "上传";
        if(file.isEmpty()){
            return Rest.fail(msg,"文件为空");
        }
        final Map<String,Object> result = new HashMap<>();
        result.put("url",hweiYunOBSService.fileUpload(file,file.getOriginalFilename()) + "?x-image-process=style/image-style");
        result.put("name",file.getOriginalFilename());
        return Rest.success(msg,result);
    }

    //富文本上传
    @PostMapping("uploadEditor")
    public Object uploadEditor(@RequestParam(value = "file",required = false) MultipartFile file){
        String msg = "上传";
        Map<String,Object> returnResult = new HashMap<>();
        if(file.isEmpty()){
            returnResult.put("errno",1);
            returnResult.put("message","文件为空");
            return returnResult;
        }
        final String result = hweiYunOBSService.fileUpload(file,file.getOriginalFilename()) + "?x-image-process=style/image-style";
        returnResult.put("errno",0);
        Map<String,Object> map = new HashMap<>();
        map.put("url",result);
        map.put("alt",file.getOriginalFilename());
        map.put("href",result);
        returnResult.put("data",map);
        return returnResult;
    }

    // 删除
    @PostMapping("delete/{fileName}")
    public Rest delete(@PathVariable String fileName){
        String msg = "删除文件";
        if(StrUtil.isEmpty(fileName)){
            return Rest.fail(msg,"删除文件为空");
        }
        final boolean delete = hweiYunOBSService.delete(fileName);
        return delete ? Rest.success(msg,null) : Rest.fail(msg,null);
    }

    //批量删除
    @PostMapping("deletes")
    //@RequestParam 获取List，数组则不需要
    public Rest delete(@RequestParam("fileNames") List<String> fileNames) {
        String msg = "删除文件";
        if (ArrayUtil.isEmpty(fileNames)) {
            return Rest.fail(msg,"删除文件不存在");
        }
        final boolean delete = hweiYunOBSService.delete(fileNames);
        return delete?Rest.success(msg,null):Rest.fail(msg,null);
    }

    //文件下载
    @GetMapping("download/{fileName}")
    public Rest download(HttpServletRequest request, HttpServletResponse response,@PathVariable String fileName){
        String msg = "下载文件";
        if(StrUtil.isEmpty(fileName)){
            return Rest.fail(msg,"下载文件不存在");
        }
        //指定obs的文件存放目录
        String fileDirName = "webresource/";
        //指定下载文件的样式
        String fileExtName = "?x-image-process=style/image-style";
        try(InputStream inputStream = hweiYunOBSService.fileDownload(fileDirName + fileName); BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream())){
            if(inputStream == null){
                return Rest.fail("请求"+ msg,null);
            }
            // 为防止文件名乱码
            final String userAgent = request.getHeader("USER-AGENT");
            //IE浏览器
            if(StrUtil.contains(userAgent,"MSIE")){
                fileName = URLEncoder.encode(fileName,"UTF-8");
            }else{
                //其他浏览器
                fileName = URLEncoder.encode(fileName,"UTF-8");
            }
            response.setContentType("application/x-download");
            //设置让浏览器弹出下载提示框，而不是直接在浏览器中打开
            response.addHeader("Content-Disposition","attachment;filename=" + fileName);
            IoUtil.copy(inputStream,outputStream);
            return null;
        }catch (IOException | ObsException e){
            return Rest.fail(msg,e);
        }
    }
}
