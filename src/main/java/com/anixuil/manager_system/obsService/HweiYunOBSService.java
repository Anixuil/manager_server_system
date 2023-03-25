package com.anixuil.manager_system.obsService;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface HweiYunOBSService {
    //删除文件
    boolean delete(String objectKey);

    //批量删除文件
    boolean delete(List<String> objectKeys);

    //上传文件
    String fileUpload(MultipartFile uploadFile,String objectKey);

    //文件下载
    InputStream fileDownload(String objectKey);
}
