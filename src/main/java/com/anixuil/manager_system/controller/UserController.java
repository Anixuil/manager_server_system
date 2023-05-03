package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.UserDetail;
import com.anixuil.manager_system.entity.UserTable;
import com.anixuil.manager_system.pojo.UserAll;
import com.anixuil.manager_system.service.UserTableService;
import com.anixuil.manager_system.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    UserTableService userTableService;

    //注册
    @PostMapping("register")
    public Rest register(@RequestBody UserAll user){
        return userTableService.register(user);
    }

    @PostMapping("login")
    public Rest login(@RequestBody UserTable userTable){
        return userTableService.login(userTable);
    }

    //从token中获取用户信息
    @GetMapping("getUserInfo")
    public Rest getUserInfo(@RequestHeader("token") String token){
        return userTableService.getUserInfo(token);
    }

    //修改密码
    @PutMapping("updatePwd")
    public Rest updatePwd(@RequestBody UserTable userTable){
        return userTableService.updatePwd(userTable);
    }

    //修改用户信息
    @PutMapping("updateUser")
    public Rest updateUser(@RequestBody UserAll user){
        return userTableService.updateUserInfo(user);
    }

    //删除用户
    @DeleteMapping("deleteUser")
    public Rest deleteUser(@RequestBody UserTable userTable){
        return userTableService.deleteUser(userTable);
    }

    //获取考生列表
    @GetMapping("getCandidateList")
    public Rest getCandidateList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String userUuid,
            @RequestParam(defaultValue = "") String userName,
            @RequestParam(defaultValue = "") String userPhone,
            @RequestParam(defaultValue = "") String userEmail,
            @RequestParam(defaultValue = "") String majorUuid,
            @RequestParam(defaultValue = "") String candidateId,
            @RequestParam(defaultValue = "") String candidateStatus,
            @RequestParam(defaultValue = "") String examPlace
    ){
        return userTableService.getCandidateList(pageNum,pageSize,userUuid,userName,userPhone,userEmail,majorUuid,candidateId,candidateStatus,examPlace);
    }

    //修改考生信息
    @PutMapping("updateCandidate")
    public Rest updateCandidate(@RequestBody UserAll user){
        return userTableService.updateCandidate(user);
    }

    //获取学生列表
    @GetMapping("getStudentList")
    public Rest getStudentList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String userUuid,
            @RequestParam(defaultValue = "") String userName,
            @RequestParam(defaultValue = "") String userPhone,
            @RequestParam(defaultValue = "") String userEmail,
            @RequestParam(defaultValue = "") String majorUuid,
            @RequestParam(defaultValue = "") String studentId
    ){
        return userTableService.getStudentList(pageNum,pageSize,userUuid,userName,userPhone,userEmail,majorUuid,studentId);
    }

    //修改学生信息
    @PutMapping("updateStudent")
    public Rest updateStudent(@RequestBody UserAll user){
        return userTableService.updateStudent(user);
    }

    //获取教师列表
    @GetMapping("getTeacherList")
    public Rest getTeacherList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String userUuid,
            @RequestParam(defaultValue = "") String userName,
            @RequestParam(defaultValue = "") String userPhone,
            @RequestParam(defaultValue = "") String userEmail,
            @RequestParam(defaultValue = "") String departUuid,
            @RequestParam(defaultValue = "") String teacherId,
            @RequestParam(defaultValue = "") String classUuid
    ){
        return userTableService.getTeacherList(pageNum,pageSize,userUuid,userName,userPhone,userEmail,teacherId,departUuid,classUuid);
    }

    //修改教师信息
    @PutMapping("updateTeacher")
    public Rest updateTeacher(@RequestBody UserAll user){
        return userTableService.updateTeacher(user);
    }
}
