package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.pojo.DictData;
import com.anixuil.manager_system.entity.DepartTable;
import com.anixuil.manager_system.entity.DictFieldTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.service.DepartTableService;
import com.anixuil.manager_system.service.DictFieldTableService;
import com.anixuil.manager_system.service.DictTableService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("dict")
public class DictTableController {
    @Resource
    DictTableService dictTableService;
    @Resource
    DictFieldTableService dictFieldTableService;

    //新增字典
    @PostMapping("add")
    public Rest addDict(@RequestBody DictData dictData){
        return dictFieldTableService.addDictField(dictData);
    }

    //根据传入的字典名获取字典
    @GetMapping("item")
    public Rest getDict(String dictType,String dictName){
        return dictTableService.getDict(dictType,dictName);
    }
}
