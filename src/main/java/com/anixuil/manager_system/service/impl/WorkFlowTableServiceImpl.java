package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.WorkFlowTable;
import com.anixuil.manager_system.mapper.WorkFlowTableMapper;
import com.anixuil.manager_system.service.WorkFlowTableService;
import com.anixuil.manager_system.utils.Datetime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class WorkFlowTableServiceImpl extends ServiceImpl<WorkFlowTableMapper, WorkFlowTable> implements WorkFlowTableService {

    @Override
    public Rest addWorkFlow(WorkFlowTable workFlowTable) {
        String msg = "添加工作流";
        try {
            boolean result = save(workFlowTable);
            if(result){
                return Rest.success(msg,true);
            }
            return Rest.fail(msg,false);
        } catch (Exception e) {
            e.printStackTrace();
            return Rest.fail(msg,e);
        }
    }

    @Override
    public Rest updateWorkFlow(WorkFlowTable workFlowTable) {
        String msg = "修改工作流";
        try {
            boolean result = updateById(workFlowTable);
            if(result){
                return Rest.success(msg,true);
            }
            return Rest.fail(msg,false);
        } catch (Exception e) {
            e.printStackTrace();
            return Rest.fail(msg,e);
        }
    }

    @Override
    public Rest deleteWorkFlow(WorkFlowTable workFlowTable) {
        String msg = "删除工作流";
        try {
            boolean result = removeById(workFlowTable.getWorkFlowUuid());
            if(result){
                return Rest.success(msg,true);
            }
            return Rest.fail(msg,false);
        } catch (Exception e) {
            e.printStackTrace();
            return Rest.fail(msg,e);
        }
    }

    @Override
    public Rest getWorkFlowList(Integer pageNum, Integer pageSize,String workFlowUuid, String workFlowTitle, String workFlowDesc, String workFlowType) {
        String msg = "获取工作流列表";
        try {
            IPage<WorkFlowTable> page = new Page<>(pageNum,pageSize);
            LambdaQueryWrapper<WorkFlowTable> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.like(WorkFlowTable::getWorkFlowTitle,workFlowTitle)
                    .like(WorkFlowTable::getWorkFlowDesc,workFlowDesc)
                    .like(WorkFlowTable::getWorkFlowUuid,workFlowUuid)
                    .like(WorkFlowTable::getWorkFlowType,workFlowType);
            IPage<WorkFlowTable> workFlowTableIPage = page(page,queryWrapper);
            List<WorkFlowTable> workFlowTableList = workFlowTableIPage.getRecords();
            List<Map<String,Object>> mapList = workFlowTableList.stream().map(workFlowTable -> {
                Map<String,Object> map = new HashMap<>();
                map.put("workFlowUuid",workFlowTable.getWorkFlowUuid());
                map.put("workFlowTitle",workFlowTable.getWorkFlowTitle());
                map.put("workFlowDesc",workFlowTable.getWorkFlowDesc());
                map.put("workFlowStatus",workFlowTable.getWorkFlowStatus());
                map.put("workFlowIndex",workFlowTable.getWorkFlowIndex());
                map.put("workFlowType",workFlowTable.getWorkFlowType());
                map.put("createDate", Datetime.format(workFlowTable.getCreateDate()));
                map.put("updateDate",Datetime.format(workFlowTable.getUpdateDate()));
                map.put("isDelete",workFlowTable.getIsDelete());
                return map;
            }).collect(Collectors.toList());
            Map<String,Object> map = new HashMap<>();
            map.put("total",workFlowTableIPage.getTotal());
            map.put("records",mapList);
            map.put("pageNum",workFlowTableIPage.getCurrent());
            map.put("pageSize",workFlowTableIPage.getSize());
            map.put("pages",workFlowTableIPage.getPages());
            return Rest.success(msg,map);
        } catch (Exception e) {
            e.printStackTrace();
            return Rest.fail(msg,e);
        }
    }
}
