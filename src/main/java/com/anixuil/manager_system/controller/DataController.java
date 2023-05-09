package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.entity.*;
import com.anixuil.manager_system.service.*;
import com.anixuil.manager_system.utils.Datetime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/data")
public class DataController {
    @Resource
    TeacherTableService teacherTableService;
    @Resource
    StudentTableService studentTableService;
    @Resource
    CandidateTableService candidateTableService;
    @Resource
    DepartTableService departTableService;
    @Resource
    DictTableService dictTableService;
    @Resource
    MajorTableService majorTableService;
    @Resource
    ClassTableService classTableService;

    //获取不同角色的人数以及用户总人数
    @GetMapping("/getUserCount")
    public Rest getCount(){
        String msg = "获取用户数量";
        try{
            Map<String,Object> map = new HashMap<>();
            map.put("teacherCount",teacherTableService.count());
            map.put("studentCount",studentTableService.count());
            map.put("candidateCount",candidateTableService.count());
            map.put("userCount",teacherTableService.count()+studentTableService.count()+candidateTableService.count());
            return Rest.success(msg,map);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //获取不同院系下的学生人数
    @GetMapping("/getDepartCount")
    public Rest getDepartCount(){
        String msg = "获取院系人数";
        try{
            //分页查询 用stream流来弄出一个list 里面是所有的院系数据 用map来弄出一个list 里面是所有的专业数据 专业数据里添加一个字段 studentCount 用来存放专业下的学生数量
            LambdaQueryWrapper<DepartTable> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(DepartTable::getDepartUuid,DepartTable::getDepartName,DepartTable::getDepartIntro,DepartTable::getCreateDate,DepartTable::getUpdateDate,DepartTable::getIsDelete);
            List<DepartTable> departTableList = departTableService.list();
            List<Map<String,Object>> mapList = departTableList.stream().map(departTable -> {
                Map<String,Object> map = new HashMap<>();
                map.put("departName",departTable.getDepartName());
                AtomicLong studentCount = new AtomicLong();
                AtomicLong TeacherCount = new AtomicLong();
                majorTableService.list(new LambdaQueryWrapper<MajorTable>().eq(MajorTable::getDepartUuid,departTable.getDepartUuid())).stream().forEach(majorTable -> {
                    studentCount.addAndGet(studentTableService.count(new LambdaQueryWrapper<StudentTable>().eq(StudentTable::getMajorUuid, majorTable.getMajorUuid())));
                    classTableService.list(new LambdaQueryWrapper<ClassTable>().eq(ClassTable::getMajorUuid,majorTable.getMajorUuid())).stream().forEach(classTable -> {
                        TeacherCount.addAndGet(teacherTableService.count(new LambdaQueryWrapper<TeacherTable>().eq(TeacherTable::getClassUuid, classTable.getClassUuid())));
                    });
                });
                map.put("userCount",studentCount.get() + TeacherCount.get());
                return map;
            }).collect(Collectors.toList());
            Map<String,Object> map = new HashMap<>();
            AtomicLong allCount = new AtomicLong();
            mapList.stream().forEach(map1 -> {
                allCount.addAndGet((Long) map1.get("userCount"));
            });
            map.put("userCount",allCount.get());
            map.put("departName","学校总人数");
            mapList.add(map);
            return Rest.success(msg,mapList);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }
}
