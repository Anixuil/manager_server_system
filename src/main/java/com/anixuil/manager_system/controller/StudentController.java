package com.anixuil.manager_system.controller;


import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.StudentTable;
import com.anixuil.manager_system.service.StudentTableService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("student")
public class StudentController {

    @Resource
    StudentTableService studentTableService;

    //修改学生信息
    @PutMapping("updateStudent")
    public Rest updateStudent(@RequestBody StudentTable studentTable){
        return studentTableService.updateStudent(studentTable);
    }

}
