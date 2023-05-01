package com.anixuil.manager_system.controller;


import com.anixuil.manager_system.entity.ExamClassTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.service.ExamClassTableService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/examClass")
public class ExamClassController {
    @Resource
    ExamClassTableService examClassTableService;

    //新增考试科目
    @PostMapping("/addExamClass")
    public Rest addExamClass(@RequestBody ExamClassTable examClassTable){
        return examClassTableService.addExamClass(examClassTable);
    }

    //修改考试科目
    @PutMapping("/updateExamClass")
    public Rest updateExamClass(@RequestBody ExamClassTable examClassTable){
        return examClassTableService.updateExamClass(examClassTable);
    }

    //删除考试科目
    @DeleteMapping("/deleteExamClass")
    public Rest deleteExamClass(@RequestBody ExamClassTable examClassTable){
        return examClassTableService.deleteExamClass(examClassTable);
    }

    //查询考试科目
    @PostMapping("/getExamClassList")
    public Rest getExamClassList(@RequestBody Map<String,Object> params){
        return examClassTableService.getExamClassList(params);
    }
}
