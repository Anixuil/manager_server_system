package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.ExamClassTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.mapper.ExamClassTableMapper;
import com.anixuil.manager_system.service.ExamClassTableService;
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
public class ExamClassTableServiceImpl extends ServiceImpl<ExamClassTableMapper, ExamClassTable> implements ExamClassTableService {

    @Resource
    MajorTableService majorTableService;
    @Override
    public Rest addExamClass(ExamClassTable examClassTable) {
        String msg = "添加考试科目";
        try{
            boolean result = save(examClassTable);
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
    public Rest updateExamClass(ExamClassTable examClassTable) {
        String msg = "修改考试科目";
        try{
            boolean result = updateById(examClassTable);
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
    public Rest deleteExamClass(ExamClassTable examClassTable) {
        String msg = "删除考试科目";
        try{
            boolean result = removeById(examClassTable);
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
    public Rest getExamClassList(Integer pageNum, Integer pageSize, String majorUuid, String examClassName,String examType) {
        String msg = "查询考试科目";
        try{
            IPage<ExamClassTable> page = new Page<>(pageNum,pageSize);
            LambdaQueryWrapper<ExamClassTable> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(ExamClassTable::getExamClassUuid,ExamClassTable::getExamClassName,ExamClassTable::getMajorUuid,ExamClassTable::getExamClassDesc,ExamClassTable::getExamType,ExamClassTable::getCreateDate,ExamClassTable::getUpdateDate,ExamClassTable::getIsDelete)
                    .like(ExamClassTable::getMajorUuid,majorUuid)
                    .like(ExamClassTable::getExamClassName,examClassName)
                    .like(ExamClassTable::getExamType,examType);
            IPage<ExamClassTable> examClassTableIPage = page(page,wrapper);
            List<ExamClassTable> examClassTableList = examClassTableIPage.getRecords();
            List<Map<String,Object>> mapList = examClassTableList.stream().map(examClassTable -> {
                Map<String,Object> map = new HashMap<>();
                map.put("examClassUuid",examClassTable.getExamClassUuid());
                map.put("examClassName",examClassTable.getExamClassName());
                map.put("majorUuid",examClassTable.getMajorUuid());
                map.put("majorName",majorTableService.getById(examClassTable.getMajorUuid()).getMajorName());
                map.put("examClassDesc",examClassTable.getExamClassDesc());
                map.put("examType",examClassTable.getExamType());
                map.put("createDate", Datetime.format(examClassTable.getCreateDate()));
                map.put("updateDate",Datetime.format(examClassTable.getUpdateDate()));
                map.put("isDelete",examClassTable.getIsDelete());
                return map;
            }).collect(Collectors.toList());
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("total",examClassTableIPage.getTotal());
            resultMap.put("records",mapList);
            resultMap.put("currentPage",examClassTableIPage.getCurrent());
            resultMap.put("pageSize",examClassTableIPage.getSize());
            resultMap.put("pages",examClassTableIPage.getPages());
            return Rest.success(msg,resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return Rest.error(msg,e);
        }
    }
}
