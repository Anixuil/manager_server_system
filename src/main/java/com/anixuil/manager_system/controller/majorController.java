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
@RequestMapping("major")
public class majorController {
    @Resource
    MajorTableService majorTableService;
    @Resource
    StudentTableService studentTableService;
    @Resource
    DepartTableService departTableService;

    //新增专业
    @PostMapping("addMajor")
    public Rest addMajor(@RequestBody MajorTable majorTable){
        return majorTableService.addMajor(majorTable);
    }

    //修改专业
    @PutMapping("updateMajor")
    public Rest updateMajor(@RequestBody MajorTable majorTable){
        return majorTableService.updateMajor(majorTable);
    }

    //删除专业
    @DeleteMapping("deleteMajor")
    public Rest deleteMajor(@RequestBody MajorTable majorTable){
        return majorTableService.deleteMajor(majorTable);
    }

    //获取所有专业
    @GetMapping("getAllMajor")
    public Rest getAllMajor(Integer pageNum,Integer pageSize){
        return majorTableService.getMajorList(pageNum,pageSize);
    }

}
