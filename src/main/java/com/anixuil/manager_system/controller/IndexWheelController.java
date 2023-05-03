package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.entity.IndexWheelTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.service.IndexWheelTableService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping({"indexWheel"})
public class IndexWheelController {
    @Resource
    IndexWheelTableService indexWheelTableService;

    //新增轮播
    @PostMapping({"addIndexWheel"})
    public Rest addIndexWheel(@RequestBody IndexWheelTable indexWheelTable) {
        return this.indexWheelTableService.addIndexWheel(indexWheelTable);
    }

    //修改轮播
    @PutMapping({"updateIndexWheel"})
    public Rest updateIndexWheel(@RequestBody IndexWheelTable indexWheelTable) {
        return this.indexWheelTableService.updateIndexWheel(indexWheelTable);
    }

    //删除轮播
    @DeleteMapping({"deleteIndexWheel"})
    public Rest deleteIndexWheel(@RequestBody IndexWheelTable indexWheelTable) {
        return this.indexWheelTableService.deleteIndexWheel(indexWheelTable);
    }

    //获取轮播列表
    @GetMapping({"getIndexWheelList"})
    public Rest getIndexWheelList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return this.indexWheelTableService.getIndexWheelList(pageNum, pageSize);
    }

    //获取轮播列表
    @GetMapping({"indexWheelList"})
    public Rest indexWheelList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return this.indexWheelTableService.getIndexWheelList(pageNum, pageSize);
    }

}
