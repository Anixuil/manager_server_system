package com.anixuil.manager_system.controller;


import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.WorkFlowTable;
import com.anixuil.manager_system.service.WorkFlowTableService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("workFlow")
public class WorkFlowController {
    @Resource
    WorkFlowTableService workFlowTableService;

    //添加工作流
    @PostMapping("addWorkFlow")
    public Rest addWorkFlow(@RequestBody WorkFlowTable workFlowTable) {
        return workFlowTableService.addWorkFlow(workFlowTable);
    }

    //修改工作流
    @PutMapping("updateWorkFlow")
    public Rest updateWorkFlow(@RequestBody WorkFlowTable workFlowTable) {
        return workFlowTableService.updateWorkFlow(workFlowTable);
    }

    @DeleteMapping("deleteWorkFlow")
    public Rest deleteWorkFlow(@RequestBody WorkFlowTable workFlowTable) {
        return workFlowTableService.deleteWorkFlow(workFlowTable);
    }

    //获取所有工作流
    @GetMapping("getWorkFlowList")
    public Rest getWorkFlowList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String workFlowUuid,
            @RequestParam(defaultValue = "") String workFlowTitle,
            @RequestParam(defaultValue = "") String workFlowDesc,
            @RequestParam(defaultValue = "") String workFlowType,
            @RequestParam(defaultValue = "") Boolean workFlowStatus) {
        return workFlowTableService.getWorkFlowList(pageNum, pageSize,workFlowUuid, workFlowTitle, workFlowDesc, workFlowType, workFlowStatus);
    }

    //研招时间线
    @GetMapping("getYanZhaoTimeLine")
    public Rest getYanZhaoTimeLine(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String workFlowUuid,
            @RequestParam(defaultValue = "") String workFlowTitle,
            @RequestParam(defaultValue = "") String workFlowDesc,
            @RequestParam(defaultValue = "") String workFlowType,
            @RequestParam(defaultValue = "") Boolean workFlowStatus
    ) {
        return workFlowTableService.getWorkFlowList(pageNum, pageSize, workFlowUuid, workFlowTitle, workFlowDesc, workFlowType, workFlowStatus);
    }

}
