package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.entity.LogTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.service.LogTableService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/log")
public class LogController {
    @Resource
    LogTableService logTableService;

    @PostMapping("/addLog")
    public Rest addLog(@RequestBody LogTable logTable, @RequestHeader("token") String token){
        return logTableService.addLog(logTable,token);
    }

    @GetMapping("/getLogList")
    public Rest getLogList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String logUuid,
            @RequestParam(defaultValue = "") String userUuid,
            @RequestParam(defaultValue = "") String logTitle,
            @RequestParam(defaultValue = "") String logContent,
            @RequestParam(defaultValue = "") String logStatus
    ){
        return logTableService.getLogList(pageNum,pageSize,logUuid,userUuid,logTitle,logContent,logStatus);
    }

}
