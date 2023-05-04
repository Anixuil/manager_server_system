package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.entity.DepartTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.StudentTable;
import com.anixuil.manager_system.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//    //获取不同院系下的学生人数
//    @GetMapping("/getDepartCount")
//    public Rest getDepartCount(){
//        String msg = "获取院系人数";
//        try{
//            //获取所有院系
//            LambdaQueryWrapper<DepartTable> queryWrapper = new LambdaQueryWrapper<>();
//            List<DepartTable> departTableList = departTableService.list(queryWrapper);
//            List<Map<String,Object>> mapList = departTableList.stream().map(departTable -> {
//                Map<String,Object> map = new HashMap<>();
//                map.put("departName",departTable.getDepartName());
//                map.put("departCount",studentTableService.count(new LambdaQueryWrapper<StudentTable>().eq(StudentTable::getDepart,departTable.getDepartId())));
//                return map;
//            }).collect(Collectors.toList());
////            return Rest.success(msg,map);
//            return null;
//        }catch (Exception e){
//            return Rest.error(msg,e);
//        }
//    }
}
