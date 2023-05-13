package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.CandidateTable;
import com.anixuil.manager_system.entity.EnroPlanTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.mapper.EnroPlanTableMapper;
import com.anixuil.manager_system.service.CandidateTableService;
import com.anixuil.manager_system.service.EnroPlanTableService;
import com.anixuil.manager_system.service.MajorTableService;
import com.anixuil.manager_system.utils.Datetime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class EnroPlanTableServiceImpl extends ServiceImpl<EnroPlanTableMapper, EnroPlanTable> implements EnroPlanTableService {
    @Resource
    MajorTableService majorTableService;
    @Resource
    CandidateTableService candidateTableService;

    @Override
    public Rest addEnroPlan(EnroPlanTable enroPlanTable) {
        String msg = "添加招生计划";
        try {
            System.out.println(enroPlanTable.getMajorUuid());
            boolean insert = save(enroPlanTable);
            if (insert){
                return Rest.success(msg,true);
            }
            return Rest.fail(msg,false);
        } catch (Exception e) {
            e.printStackTrace();
            return Rest.error(msg,e);
        }
    }

    @Override
    public Rest updateEnroPlan(EnroPlanTable enroPlanTable) {
        String msg = "修改招生计划";
        try{
            Boolean update = updateById(enroPlanTable);
            if (update){
                return Rest.success(msg,true);
            }
            return Rest.fail(msg,false);
        }catch (Exception e) {
            e.printStackTrace();
            return Rest.error(msg, e);
        }
    }

    @Override
    public Rest deleteEnroPlan(EnroPlanTable enroPlanTable) {
        String msg = "删除招生计划";
        try{
            Boolean delete = removeById(enroPlanTable);
            if (delete){
                return Rest.success(msg,true);
            }
            return Rest.fail(msg,false);
        }catch (Exception e) {
            e.printStackTrace();
            return Rest.error(msg, e);
        }
    }

    @Override
    public Rest getEnroPlanList(Integer pageNum, Integer pageSize) {
        String msg = "查询招生计划";
        try{
            IPage<EnroPlanTable> page = new Page<>(pageNum, pageSize);
            LambdaQueryWrapper<EnroPlanTable> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(EnroPlanTable::getEnroPlanUuid,EnroPlanTable::getMajorUuid,EnroPlanTable::getEnroPlanNumber,EnroPlanTable::getEnroRealNumber,EnroPlanTable::getCreateDate,EnroPlanTable::getUpdateDate,EnroPlanTable::getIsDelete)
                    .orderByDesc(EnroPlanTable::getCreateDate);
            IPage<EnroPlanTable> enroPlanTableIPage = page(page, wrapper);
            List<EnroPlanTable> enroPlanTableList = enroPlanTableIPage.getRecords();
            List<Map<String,Object>> mapList = enroPlanTableList.stream().map(enroPlanTable -> {
                Map<String,Object> map = new HashMap<>();
                map.put("enroPlanUuid",enroPlanTable.getEnroPlanUuid());
                map.put("majorUuid",enroPlanTable.getMajorUuid());
                map.put("majorName",majorTableService.getById(enroPlanTable.getMajorUuid()).getMajorName());
                map.put("enroPlanNumber",enroPlanTable.getEnroPlanNumber());
                map.put("enroRealNumber",candidateTableService.count(new LambdaQueryWrapper<CandidateTable>().eq(CandidateTable::getMajorUuid,enroPlanTable.getMajorUuid())));
                map.put("createDate", Datetime.format(enroPlanTable.getCreateDate()));
                map.put("updateDate",Datetime.format(enroPlanTable.getUpdateDate()));
                map.put("isDelete",enroPlanTable.getIsDelete());
                return map;
            }).collect(Collectors.toList());
            Map<String,Object> map = new HashMap<>();
            //分页数据
            map.put("total",enroPlanTableIPage.getTotal());
            map.put("currentPage",enroPlanTableIPage.getCurrent());
            map.put("pageSize",enroPlanTableIPage.getSize());
            map.put("pages",enroPlanTableIPage.getPages());
            map.put("records",mapList);
            return Rest.success(msg,map);
        }catch (Exception e) {
            e.printStackTrace();
            return Rest.error(msg, e);
        }
    }

    @Override
    public Rest getEnroPlanList(Integer pageNum, Integer pageSize,EnroPlanTable params) {
        String msg = "查询招生计划";
        try{
            IPage<EnroPlanTable> page = new Page<>(pageNum, pageSize);
            LambdaQueryWrapper<EnroPlanTable> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(EnroPlanTable::getEnroPlanUuid,EnroPlanTable::getMajorUuid,EnroPlanTable::getEnroPlanNumber,EnroPlanTable::getEnroRealNumber,EnroPlanTable::getCreateDate,EnroPlanTable::getUpdateDate,EnroPlanTable::getIsDelete)
                    .eq(EnroPlanTable::getMajorUuid,params.getMajorUuid())
                    .orderByDesc(EnroPlanTable::getCreateDate);
            IPage<EnroPlanTable> enroPlanTableIPage = page(page, wrapper);
            List<EnroPlanTable> enroPlanTableList = enroPlanTableIPage.getRecords();
            List<Map<String,Object>> mapList = enroPlanTableList.stream().map(enroPlanTable -> {
                Map<String,Object> map = new HashMap<>();
                map.put("enroPlanUuid",enroPlanTable.getEnroPlanUuid());
                map.put("majorUuid",enroPlanTable.getMajorUuid());
                map.put("majorName",majorTableService.getById(enroPlanTable.getMajorUuid()).getMajorName());
                map.put("enroPlanNumber",enroPlanTable.getEnroPlanNumber());
                map.put("enroRealNumber",enroPlanTable.getEnroRealNumber());
                map.put("createDate", Datetime.format(enroPlanTable.getCreateDate()));
                map.put("updateDate",Datetime.format(enroPlanTable.getUpdateDate()));
                map.put("isDelete",enroPlanTable.getIsDelete());
                return map;
            }).collect(Collectors.toList());
            Map<String,Object> map = new HashMap<>();
            //分页数据
            map.put("total",enroPlanTableIPage.getTotal());
            map.put("currentPage",enroPlanTableIPage.getCurrent());
            map.put("pageSize",enroPlanTableIPage.getSize());
            map.put("pages",enroPlanTableIPage.getPages());
            map.put("records",mapList);
            return Rest.success(msg,map);
        }catch (Exception e) {
            e.printStackTrace();
            return Rest.error(msg, e);
        }
    }
}
