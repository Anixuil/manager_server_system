package com.anixuil.manager_system.obsConfig;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Slf4j
@Configuration
public class HweiOBSConfig {
    // 访问密钥
    @Value("${hwyun.obs.accessKey}")
    private String accessKey;

    // 访问密钥 sk
    @Value("${hwyun.obs.securityKey}")
    private String securityKey;

    // 终端节点
    @Value("${hwyun.obs.endPoint}")
    private String endPoint;

    // 桶
    @Value("${hwyun.obs.bucketName}")
    private String bucketName;

    // 获取obs客户端实例
    public ObsClient getInstance(){
        return new ObsClient(accessKey,securityKey,endPoint);
    }

    // 销毁obs客户端实例
    public void destroy(ObsClient obsClient){
        try{
            obsClient.close();
        }catch(ObsException e){
            log.error("obs执行失败",e);
        }catch (Exception e){
            log.error("执行失败",e);
        }
    }

    // 微服务文件存放路径
    public static String getObjectKey(){
        // 项目或服务名称 + 日期存储方式
        return "Hwei" + "/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "/";
    }
}
