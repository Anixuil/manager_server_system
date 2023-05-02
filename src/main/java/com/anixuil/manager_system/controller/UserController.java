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
}
