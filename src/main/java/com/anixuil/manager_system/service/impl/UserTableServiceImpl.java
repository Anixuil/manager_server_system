package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.UserTable;
import com.anixuil.manager_system.mapper.UserTableMapper;
import com.anixuil.manager_system.service.UserTableService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
