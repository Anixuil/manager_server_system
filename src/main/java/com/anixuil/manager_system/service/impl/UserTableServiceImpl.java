package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.UserDetail;
import com.anixuil.manager_system.entity.UserTable;
import com.anixuil.manager_system.mapper.UserTableMapper;
import com.anixuil.manager_system.service.UserTableService;
import com.anixuil.manager_system.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anixuil
 * @since 2023-03-09
 */
@Service
public class UserTableServiceImpl extends ServiceImpl<UserTableMapper, UserTable> implements UserTableService {

        @Autowired
        private UserTableMapper userTableMapper;

        @Autowired
        private AuthenticationManager authenticationManager;

        //用户登录
        @Override
        public Rest login(UserTable userTable){
            String msg = "用户登录";
            try{
                //authentication 进行用户验证
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userTable.getUserName(), userTable.getUserPassword());
                Authentication authenticate = authenticationManager.authenticate(authenticationToken);
                if(Objects.isNull(authenticate)){
                    return Rest.fail(msg,"验证失败");
                }
//            如果认证通过了，使用userId生成一个Jwt
                UserDetail userDetail = (UserDetail) authenticate.getPrincipal();
                String token = JwtUtils.createJWT(userDetail.getUserUuid());
                Map<String, Object> map = new HashMap<>();
                map.put("token",token);
                return Rest.success(msg, map);
            }catch (Exception e){
                return Rest.error(msg,e);
            }
        }

    @Override
    public Rest register(UserTable userTable) {
        return null;
    }

    //获取当前用户信息
    @Override
    public Rest getUserInfo(String token) {
        String msg = "获取用户信息";
        try{
            JwtUtils jwtUtils = new JwtUtils();
            String userUuid = jwtUtils.parseJWT(token).getSubject();
            UserTable userTable = userTableMapper.selectOne(
                    new LambdaQueryWrapper<UserTable>().eq(UserTable::getUserUuid,userUuid)
            );
            Map<String,Object> info = new HashMap<>();
            info.put("user_uuid",userTable.getUserUuid());
            info.put("user_name",userTable.getUserName());
            info.put("user_email",userTable.getUserEmail());
            info.put("user_phone",userTable.getUserPhone());
            info.put("user_role",userTable.getUserRole());
            info.put("user_age",userTable.getUserAge());
            info.put("user_gender",userTable.getUserGender());
            info.put("create_date",userTable.getCreateDate());
            info.put("update_date",userTable.getUpdateDate());
            Map<String,Object> data = new HashMap<>();
            data.put("userInfo",info);
            if(userUuid != null){
                return Rest.success(msg,data);
            }
            return Rest.fail(msg,null);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //验证用户是否存在
        @Override
        public Boolean verifyUser(UserTable userTable){
            QueryWrapper<UserTable> wrapper = new QueryWrapper<>();
            wrapper.eq("user_name",userTable.getUserName());

            return userTableMapper.selectCount(wrapper) > 0;
        }

    @Override
    public Boolean verifyPwd(UserTable userTable) {
        return null;
    }


}
