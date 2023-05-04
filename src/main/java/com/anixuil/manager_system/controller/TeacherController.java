package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.TeacherTable;
import com.anixuil.manager_system.service.TeacherTableService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("teacher")
public class TeacherController {
    @Resource
    TeacherTableService teacherTableService;

    //修改教师信息
    @PutMapping("updateTeacher")
    public Rest updateTeacher(@RequestBody TeacherTable teacherTable){
        return teacherTableService.updateTeacher(teacherTable);
    }

}
