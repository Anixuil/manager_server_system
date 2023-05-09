package com.anixuil.manager_system.service;

import com.anixuil.manager_system.entity.LogTable;
import com.anixuil.manager_system.entity.Rest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anixuil
 * @since 2023-03-09
 */
public interface LogTableService extends IService<LogTable> {
    //新增日志
    Rest addLog(LogTable logTable,String token);

    //获取日志列表
    Rest getLogList(Integer pageNum,Integer pageSize,String logUuid,String userUuid,String logTitle,String logContent,String logStatus);
}
