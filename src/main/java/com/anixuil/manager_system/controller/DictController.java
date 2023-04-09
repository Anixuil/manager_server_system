package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.entity.DepartTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.service.DepartTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("dict")
public class DictController {
    @Autowired
    DepartTableService departTableService;

    //获取院系字典
    @GetMapping("depart")
    public Rest getDepartDict(){
        String msg = "获取院系字典";
        try{
            List<DepartTable> departTableList = departTableService.list();
            List<Map<String,Object>> mapList = departTableList.stream().map(departTable -> {
                Map<String,Object> map = new HashMap<>();
                map.put("departUuid",departTable.getDepartUuid());
                map.put("departName",departTable.getDepartName());
                return map;
            }).collect(Collectors.toList());
            return Rest.success(msg,mapList);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }
}
