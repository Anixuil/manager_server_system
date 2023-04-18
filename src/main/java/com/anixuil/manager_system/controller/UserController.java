package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.UserDetail;
import com.anixuil.manager_system.entity.UserTable;
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
    public Rest register(@RequestBody UserTable userTable){
        String msg = "用户注册";
        try{
            //用户是否已经存在
            Boolean isExits = userTableService.verifyUser(userTable);
            if(isExits){
                return Rest.fail(msg,"用户已经存在");
            }
            //密码加密
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            userTable.setUserPassword(bCryptPasswordEncoder.encode(userTable.getUserPassword()));
            Boolean result = userTableService.save(userTable);
            if(result){
                return Rest.success(msg, true);
            }
            return Rest.fail(msg, false);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
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
}
