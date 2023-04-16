package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.UserDetail;
import com.anixuil.manager_system.entity.UserTable;
import com.anixuil.manager_system.mapper.UserTableMapper;
import com.anixuil.manager_system.service.UserTableService;
import com.anixuil.manager_system.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

        //验证用户是否存在
        @Override
        public Boolean verifyUser(UserTable userTable){
            QueryWrapper<UserTable> wrapper = new QueryWrapper<>();
            wrapper.eq("user_name",userTable.getUserName());

            return userTableMapper.selectCount(wrapper) > 0;
        }

        //验证密码是否正确
        @Override
        public Boolean verifyPwd(UserTable userTable){
            //实例化加密模块
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            //拿到用户当前输入的密码
            String currentPwd = userTable.getUserPassword();
            //对用户进行查询并拿到用户信息
            QueryWrapper<UserTable> wrapper = new QueryWrapper<>();
            //查询条件
            wrapper.eq("user_name",userTable.getUserName());
            //查询结果
            UserTable userInfo = userTableMapper.selectOne(wrapper);
            String oldPwd = userInfo.getUserPassword();
            return bCryptPasswordEncoder.matches(currentPwd,oldPwd);
        }


}
