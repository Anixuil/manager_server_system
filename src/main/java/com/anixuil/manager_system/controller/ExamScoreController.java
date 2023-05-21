package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.service.ExamScoreTableService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/examScore")
public class ExamScoreController {
    @Resource
    ExamScoreTableService examScoreTableService;

    //查询个人考试成绩
    @GetMapping("/getExamScore")
    public Rest getExamScore(@RequestHeader("token") String token){
        return examScoreTableService.getExamScore(token);
    }
}
