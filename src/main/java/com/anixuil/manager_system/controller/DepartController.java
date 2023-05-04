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

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("depart")
public class DepartController {
    @Resource
    DepartTableService departTableService;

    @Resource
    MajorTableService majorTableService;

    @Resource
    StudentTableService studentTableService;

    //获取所有院系
    @GetMapping("getAllDepart")
    public Rest getAllDepart(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize){
        return departTableService.getAllDepart(pageNum,pageSize);
    }

    //新建院系
    @PostMapping("addDepart")
    public Rest addDepart(@RequestBody DepartTable departTable){
       return departTableService.addDepart(departTable);
    }

    //修改院系
    @PutMapping("updateDepart")
    public Rest updateDepart(@RequestBody DepartTable departTable){
        return departTableService.updateDepart(departTable);
    }

    //删除院系
    @DeleteMapping("deleteDepart")
    public Rest deleteDepart(@RequestBody DepartTable departTable){
        return departTableService.deleteDepart(departTable);
    }


}
