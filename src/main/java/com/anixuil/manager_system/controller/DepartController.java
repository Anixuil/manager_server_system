package com.anixuil.manager_system.controller;


import com.anixuil.manager_system.entity.DepartTable;
import com.anixuil.manager_system.entity.MajorTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.StudentTable;
import com.anixuil.manager_system.service.DepartTableService;
import com.anixuil.manager_system.service.MajorTableService;
import com.anixuil.manager_system.service.StudentTableService;
import com.anixuil.manager_system.utils.Datetime;
import com.anixuil.manager_system.utils.Uuid;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("depart")
public class DepartController {
    @Autowired
    DepartTableService departTableService;

    @Autowired
    MajorTableService majorTableService;

    @Autowired
    StudentTableService studentTableService;

    //获取所有院系
    @GetMapping("getAllDepart")
    public Rest getAllDepart(Integer pageNum,Integer pageSize){
        String msg = "获取所有院系";
        try{
            IPage<DepartTable> page = new Page<>(pageNum,pageSize);
            //分页查询 用stream流来弄出一个list 里面是所有的院系数据 用map来弄出一个list 里面是所有的专业数据 专业数据里添加一个字段 studentCount 用来存放专业下的学生数量
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.select("depart_uuid","depart_name","depart_intro","create_date");
            IPage<DepartTable> departTableIPage = departTableService.page(page, queryWrapper);
            List<DepartTable> departTableList = departTableIPage.getRecords();
            List<Map<String,Object>> mapList = departTableList.stream().map(departTable -> {
                Map<String,Object> map = new HashMap<>();
                map.put("departUuid",departTable.getDepartUuid());
                map.put("departName",departTable.getDepartName());
                map.put("departIntro",departTable.getDepartIntro());
                //格式化一下departTable.getCreateDate()的时间戳
                map.put("createDate", Datetime.format(departTable.getCreateDate()));
                map.put("majorList",majorTableService.list(new QueryWrapper<MajorTable>().eq("depart_uuid",departTable.getDepartUuid())).stream().map(majorTable -> {
                    Map<String,Object> majorMap = new HashMap<>();
                    majorMap.put("majorUuid",majorTable.getMajorUuid());
                    majorMap.put("majorName",majorTable.getMajorName());
                    majorMap.put("majorIntro",majorTable.getMajorIntro());
                    //格式化一下majorTable.getCreateDate()的时间戳
                    majorMap.put("createDate", Datetime.format(majorTable.getCreateDate()));
                    majorMap.put("studentCount",studentTableService.count(new QueryWrapper<StudentTable>().eq("major_uuid",majorTable.getMajorUuid())));
                    return majorMap;
                }).collect(Collectors.toList()));
                return map;
            }).collect(Collectors.toList());
            //返回的数据
            Map<String,Object> map = new HashMap<>();
            //分页数据
            map.put("total",departTableIPage.getTotal());
            map.put("currentPage",departTableIPage.getCurrent());
            map.put("pageSize",departTableIPage.getSize());
            map.put("pages",departTableIPage.getPages());
            map.put("records",mapList);
            return Rest.success(msg,map);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //新建院系
    @PostMapping("addDepart")
    public Rest addDepart(@RequestBody DepartTable departTable){
        String msg = "新建院系";
        try{
            departTable.setDepartUuid(Uuid.getUuid());
            departTable.setCreateDate(Datetime.getTimestamp());
            departTable.setUpdateDate(departTable.getCreateDate());
            Boolean result = departTableService.save(departTable);
            if(result){
                return Rest.success(msg, true);
            }
            return Rest.fail(msg, false);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //修改院系
    @PutMapping("updateDepart")
    public Rest updateDepart(@RequestBody DepartTable departTable){
        String msg = "修改院系";
        try{
            departTable.setUpdateDate(Datetime.getTimestamp());
            Boolean result = departTableService.updateById(departTable);
            if(result){
                return Rest.success(msg, true);
            }
            return Rest.fail(msg, false);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //删除院系
    @DeleteMapping("deleteDepart")
    public Rest deleteDepart(@RequestBody DepartTable departTable){
        String msg = "删除院系";
        try{
            String departUuid = departTable.getDepartUuid();
            System.out.println(departUuid);
            Boolean result = departTableService.remove(new QueryWrapper<DepartTable>().eq("depart_uuid",departUuid));
            if(result){
                return Rest.success(msg, true);
            }
            return Rest.fail(msg, false);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }
}
