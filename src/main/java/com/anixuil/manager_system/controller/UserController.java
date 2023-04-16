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
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserTableService userTableService;

    private AuthenticationManager authenticationManager;


//    private RedisCache redisCache;

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

        String msg = "用户登录";
        try {
            //authentication 进行用户验证
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userTable.getUserName(), userTable.getUserPassword());
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            if(Objects.isNull(authenticate)){
                return Rest.fail(msg,"验证失败");
            }
//            如果认证通过了，使用userId生成一个Jwt
            UserDetail userDetail = (UserDetail) authenticate.getPrincipal();
            String token = JwtUtils.createJWT(userDetail.getUserUuid());
//            将token存入redis
//            redisCache.setCacheObject("login:" + userDetail.getUserUuid(),userDetail);
            return Rest.success(msg, token);
//            return Rest.success(msg, true);
        }catch (Exception e){
            return Rest.error(msg,e);
        }

//        String msg = "用户登录";
//        JwtUtils jwtUtils = new JwtUtils();
//        try{
//            //当前用户是否存在
//            Boolean isExits = userTableService.verifyUser(userTable);
//            if(!isExits){
//                return Rest.fail("用户不存在",null);
//            }
//            //密码是否正确
//            Boolean isSimple = userTableService.verifyPwd(userTable);
//            if(!isSimple){
//                return Rest.fail("密码错误",null);
//            }
//            QueryWrapper<UserTable> wrapper = new QueryWrapper<>();
//            wrapper.eq("user_name",userTable.getUserName())
//                    .select("user_uuid","user_name");
//            UserTable user = userTableService.getOne(wrapper);
//            Map<String,Object> info = new HashMap<>();
//            info.put("userUuid",user.getUserUuid());
//            info.put("userName",user.getUserName());
////            System.out.println(info);
//            Map<String,Object> data = new HashMap<>();
//            data.put("userInfo",info);
//            data.put("token",jwtUtils.createToken(info));
//            if(info != null){
//                return Rest.success(msg,data);
//            }
//            return Rest.fail(msg,null);
//        }catch (Exception e){
//            return Rest.error(msg,e);
//        }
    }

    //从token中获取用户信息
    @GetMapping("getUserInfo")
    public Rest getUserInfo(@RequestHeader("token") String token){
        String msg = "获取用户信息";
        try{
            JwtUtils jwtUtils = new JwtUtils();
            System.out.println(token);
            Map<String,Object> info = jwtUtils.parseJWT(token);
            if(info != null){
                return Rest.success(msg,info);
            }
            return Rest.fail(msg,null);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }
}
