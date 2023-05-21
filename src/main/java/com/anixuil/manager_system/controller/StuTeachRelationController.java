package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.service.StuTeachRelationTableService;
import com.anixuil.manager_system.service.StudentTableService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/stuTeachRelation")
public class StuTeachRelationController {
    @Resource
    StuTeachRelationTableService stuTeachRelationTableService;

    //获取与当前用户相关的关系
    @GetMapping("/getStuTeachRelation")
    public Rest getStuTeachRelation(
            @RequestHeader("token") String token,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        return stuTeachRelationTableService.getStuTeachRelation(token,pageNum,pageSize);
    }

    //选择老师
    @PostMapping("/chooseTeacher")
    public Rest chooseTeacher(
            @RequestHeader("token") String token,
            @RequestParam("teacherUuid") String teacherUuid
    ){
        return stuTeachRelationTableService.chooseTeacher(token,teacherUuid);
    }

    //教师同意
    @PostMapping("/agree")
    public Rest agree(
            @RequestHeader("token") String token,
            @RequestParam("relationUuid") String relationUuid,
            @RequestParam("relationType") String relationType
    ){
        return stuTeachRelationTableService.agree(token,relationUuid,relationType);
    }

}
