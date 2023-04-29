package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.ClassTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.TeacherTable;
import com.anixuil.manager_system.mapper.ClassTableMapper;
import com.anixuil.manager_system.service.ClassTableService;
import com.anixuil.manager_system.service.MajorTableService;
import com.anixuil.manager_system.service.TeacherTableService;
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
public class ClassTableServiceImpl extends ServiceImpl<ClassTableMapper, ClassTable> implements ClassTableService {

    @Resource
    MajorTableService majorTableService;

    @Override
    public Rest addClass(ClassTable classTable) {
        String msg = "添加课程";
        try {
            Boolean result = save(classTable);
            if (result) {
                return Rest.success(msg,true);
            }
            return Rest.fail(msg,false);
        } catch (Exception e) {
            e.printStackTrace();
            return Rest.fail(msg,false);
        }
    }

    @Override
    public Rest updateClass(ClassTable classTable) {
        String msg = "修改课程";
        try {
            updateById(classTable);
            return new Rest(200, msg + "成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Rest(500, msg + "失败", null);
        }
    }

    @Override
    public Rest deleteClass(ClassTable classTable) {
        String msg = "删除课程";
        try {
            removeById(classTable.getClassUuid());
            return new Rest(200, msg + "成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Rest(500, msg + "失败", null);
        }
    }

    @Override
    public Rest getClassList(Integer pageNum,Integer pageSize) {
        String msg = "获取课程列表";
        try {
            IPage<ClassTable> page = new Page<>(pageNum,pageSize);
            LambdaQueryWrapper<ClassTable> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(ClassTable::getClassUuid,ClassTable::getClassName,ClassTable::getClassIntro,ClassTable::getMajorUuid,ClassTable::getCreateDate,ClassTable::getUpdateDate);
            IPage<ClassTable> classTableIPage = page(page, wrapper);
            List<ClassTable> classTableList = classTableIPage.getRecords();
            List<Map<String,Object>> mapList = classTableList.stream().map(classTable -> {
                Map<String,Object> map = new java.util.HashMap<>();
                map.put("classUuid",classTable.getClassUuid());
                map.put("className",classTable.getClassName());
                map.put("classIntro",classTable.getClassIntro());
                map.put("majorUuid",classTable.getMajorUuid());
                map.put("createDate", Datetime.format(classTable.getCreateDate()));
                map.put("updateDate",Datetime.format(classTable.getUpdateDate()));
                map.put("majorName",majorTableService.getById(classTable.getMajorUuid()).getMajorName());
                return map;
            }).collect(Collectors.toList());
            Map<String,Object> map = new HashMap<>();
            map.put("total",classTableIPage.getTotal());
            map.put("records",mapList);
            map.put("currentPage",classTableIPage.getCurrent());
            map.put("pageSize",classTableIPage.getSize());
            map.put("pages",classTableIPage.getPages());
            return Rest.success(msg,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Rest(500, msg + "失败", null);
        }
    }
}
