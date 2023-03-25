package com.anixuil.manager_system.obsService.impl;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.*;
import com.anixuil.manager_system.obsConfig.HweiOBSConfig;
import com.anixuil.manager_system.obsService.HweiYunOBSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
public class HweiYunOBSServiceImpl implements HweiYunOBSService {
    @Autowired
    private HweiOBSConfig hweiOBSConfig;

    @Override
    public boolean delete(String objectKey){
        ObsClient obsClient = null;
        try{
            //创建ObsClient实例
            obsClient = hweiOBSConfig.getInstance();
            //obs删除
            obsClient.deleteObject(hweiOBSConfig.getBucketName(),objectKey);
//            System.out.println(obsClient);
        }catch (ObsException e){
            log.error("obs删除保存失败",e);
        }finally {
            hweiOBSConfig.destroy(obsClient);
        }
        return true;
    }

    @Override
    public boolean delete(List<String> objectKeys){
        ObsClient obsClient = null;
        try{
            obsClient = hweiOBSConfig.getInstance();
            DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(hweiOBSConfig.getBucketName());
            objectKeys.forEach(x -> deleteObjectsRequest.addKeyAndVersion(x));
            //批量删除请求
            obsClient.deleteObjects(deleteObjectsRequest);
            return true;
        }catch (ObsException e){
            log.error("obs删除保存失败",e);
        }finally {
            hweiOBSConfig.destroy(obsClient);
        }
        return false;
    }

    @Override
    public String fileUpload(MultipartFile uploadFile,String objectKey){
        ObsClient obsClient = null;
        try{
            String bucketName = hweiOBSConfig.getBucketName();
            obsClient = hweiOBSConfig.getInstance();
            //判断桶是否存在
            boolean exists = obsClient.headBucket(bucketName);
            if(!exists){
                //若不存在 则创建桶
                HeaderResponse response = obsClient.createBucket(bucketName);
                log.info("创建桶成功" + response.getRequestId());
            }
            InputStream inputStream = uploadFile.getInputStream();
            long available = inputStream.available();
            PutObjectRequest request = new PutObjectRequest(bucketName,"webresource/" + objectKey,inputStream);

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(available);
            request.setMetadata(objectMetadata);
            //设置对象访问权限为公开读
            request.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ);
            PutObjectResult result = obsClient.putObject(request);

            //读取该已上传对象url
            log.info("已上传对象的url" + result.getObjectUrl());
            return result.getObjectUrl();
        }catch (ObsException e){
            log.error("obs上传失败",e);
        }catch (IOException e){
            log.error("上传失败",e);
        }finally {
            hweiOBSConfig.destroy(obsClient);
        }
        return null;
    }

    @Override
    public InputStream fileDownload(String objectKey) {
        ObsClient obsClient = null;
        try {
            String bucketName = hweiOBSConfig.getBucketName();
            obsClient = hweiOBSConfig.getInstance();
            ObsObject obsObject = obsClient.getObject(bucketName, objectKey);
            return obsObject.getObjectContent();
        } catch (ObsException e) {
            log.error("obs文件下载失败", e);
        } finally {
            hweiOBSConfig.destroy(obsClient);
        }
        return null;
    }
}
