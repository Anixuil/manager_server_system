package com.anixuil.manager_system.controller;


import com.anixuil.manager_system.entity.ClassTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.service.ClassTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/class")
public class ClassTableController {

    @Autowired
    ClassTableService classTableService;

    //添加课程
    @PostMapping("addClass")
    public Rest addClass(@RequestBody ClassTable classTable){
        return classTableService.addClass(classTable);
    }

    //修改课程
    @PutMapping("updateClass")
    public Rest updateClass(@RequestBody ClassTable classTable){
        return classTableService.updateClass(classTable);
    }

    //删除课程
    @DeleteMapping("deleteClass")
    public Rest deleteClass(@RequestBody ClassTable classTable){
        return classTableService.deleteClass(classTable);
    }

    //获取课程列表
    @GetMapping("getClassList")
    public Rest getClassList(Integer pageNum,Integer pageSize){
        return classTableService.getClassList(pageNum,pageSize);
    }
}
