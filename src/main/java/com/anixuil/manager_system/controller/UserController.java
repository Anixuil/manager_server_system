package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.UserTable;
import com.anixuil.manager_system.service.UserTableService;
import com.anixuil.manager_system.utils.Datetime;
import com.anixuil.manager_system.utils.JwtUtils;
import com.anixuil.manager_system.utils.Uuid;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
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
            userTable.setUserUuid(Uuid.getUuid());
            userTable.setCreateDate(Datetime.getTimestamp());
            Boolean result = userTableService.save(userTable);
            if(result){
                return Rest.success(msg,result);
            }
            return Rest.fail(msg,result);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    @PostMapping("login")
    public Rest login(@RequestBody UserTable userTable){
        String msg = "用户登录";
        JwtUtils jwtUtils = new JwtUtils();
        try{
            //当前用户是否存在
            Boolean isExits = userTableService.verifyUser(userTable);
            if(!isExits){
                return Rest.fail("用户不存在",null);
            }
            //密码是否正确
            Boolean isSimple = userTableService.verifyPwd(userTable);
            if(!isSimple){
                return Rest.fail("密码错误",null);
            }
            QueryWrapper<UserTable> wrapper = new QueryWrapper<>();
            wrapper.eq("user_name",userTable.getUserName());
//            UserTable info = userTableService.getOne(wrapper);
            Map<String,Object> info = userTableService.getMap(
                    new QueryWrapper<UserTable>()
                            .select("user_uuid","user_name")
                            .eq("user_name",userTable.getUserName())
            );
            System.out.println(info);
            Map<String,Object> data = new HashMap<>();
            data.put("userInfo",info);
            data.put("token",jwtUtils.createToken(info));
            if(info != null){
                return Rest.success(msg,data);
            }
            return Rest.fail(msg,null);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }
}
