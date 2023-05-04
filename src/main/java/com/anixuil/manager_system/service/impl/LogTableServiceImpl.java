package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.LogTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.mapper.LogTableMapper;
import com.anixuil.manager_system.service.LogTableService;
import com.anixuil.manager_system.utils.Datetime;
import com.anixuil.manager_system.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anixuil
 * @since 2023-03-09
 */
@Service
public class LogTableServiceImpl extends ServiceImpl<LogTableMapper, LogTable> implements LogTableService {

    @Override
    public Rest addLog(LogTable logTable,String token) {
        String msg = "新增日志";
        try{
            JwtUtils jwtUtils = new JwtUtils();
            String userUuid = jwtUtils.parseJWT(token).getSubject();
            System.out.println(userUuid);
            logTable.setUserUuid(userUuid);
            boolean result = save(logTable);
            if(result){
                return Rest.success(msg,true);
            }
            return Rest.fail(msg,false);
        }catch (Exception e){
            e.printStackTrace();
            return Rest.error(msg,e);
        }
    }

    @Override
    public Rest getLogList(Integer pageNum, Integer pageSize,String userUuid, String logTitle, String logContent, String logStatus) {
        String msg = "获取日志列表";
        try{

            IPage<LogTable> page = new Page<>(pageNum,pageSize);
            LambdaQueryWrapper<LogTable> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(
                    LogTable::getLogUuid,
                    LogTable::getLogTitle,
                    LogTable::getLogContent,
                    LogTable::getLogStatus,
                    LogTable::getCreateDate,
                    LogTable::getUserUuid
            ).like(LogTable::getLogTitle,logTitle)
                    .like(LogTable::getLogContent,logContent)
                    .like(LogTable::getUserUuid,userUuid)
                    .like(LogTable::getLogStatus,logStatus)
                    .orderByDesc(LogTable::getCreateDate);
            IPage<LogTable> logTableIPage = page(page, wrapper);
            List<LogTable>  logTableList = logTableIPage.getRecords();
            List<Map<String,Object>> mapList = logTableList.stream().map(logTable -> {
                Map<String,Object> map = new java.util.HashMap<>();
                map.put("logUuid",logTable.getLogUuid());
                map.put("logTitle",logTable.getLogTitle());
                map.put("logContent",logTable.getLogContent());
                map.put("logStatus",logTable.getLogStatus());
                map.put("createDate", Datetime.format(logTable.getCreateDate()));
                map.put("userUuid",logTable.getUserUuid());
                return map;
            }).collect(Collectors.toList());
            Map<String,Object> map = new HashMap<>();
            map.put("total",logTableIPage.getTotal());
            map.put("currentPage",logTableIPage.getCurrent());
            map.put("pageSize",logTableIPage.getSize());
            map.put("pages",logTableIPage.getPages());
            map.put("records",mapList);
            return Rest.success(msg,map);
        }catch (Exception e){
            e.printStackTrace();
            return Rest.error(msg,e);
        }
    }
}
