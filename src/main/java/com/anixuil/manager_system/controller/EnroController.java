package com.anixuil.manager_system.controller;


import com.anixuil.manager_system.entity.EnroPlanTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.service.EnroPlanTableService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/enro")
public class EnroController {
    @Resource
    EnroPlanTableService enroPlanTableService;

    //添加招生计划
    @PostMapping("/addEnroPlan")
    public Rest addEnroPlan(@RequestBody EnroPlanTable enroPlanTable){
        return enroPlanTableService.addEnroPlan(enroPlanTable);
    }

    //修改招生计划
    @PutMapping("/updateEnroPlan")
    public Rest updateEnroPlan(@RequestBody EnroPlanTable enroPlanTable){
        return enroPlanTableService.updateEnroPlan(enroPlanTable);
    }

    //删除招生计划
    @DeleteMapping("/deleteEnroPlan")
    public Rest deleteEnroPlan(@RequestBody EnroPlanTable enroPlanTable){
        return enroPlanTableService.deleteEnroPlan(enroPlanTable);
    }

    //查询招生计划
//    @GetMapping("/getEnroPlanList")
//    public Rest getEnroPlanList(Integer pageNum, Integer pageSize){
//        return enroPlanTableService.getEnroPlanList(pageNum,pageSize);
//    }

    @PostMapping("/getEnroPlanList")
    public Rest getEnroPlanList(@RequestBody Map<String,Object> params){
        Integer pageNum = (Integer) params.get("pageNum");
        Integer pageSize = (Integer) params.get("pageSize");
        if(params.get("majorUuid") != null) {
            EnroPlanTable enroPlanTable = new EnroPlanTable();
            enroPlanTable.setMajorUuid((String) params.get("majorUuid"));
            return enroPlanTableService.getEnroPlanList(pageNum, pageSize, enroPlanTable);
        }
        return enroPlanTableService.getEnroPlanList(pageNum,pageSize);
    }

}
