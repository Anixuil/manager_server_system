package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.UserDetail;
import com.anixuil.manager_system.entity.UserTable;
import com.anixuil.manager_system.mapper.UserTableMapper;
import com.anixuil.manager_system.service.UserTableService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserTableMapper userTableMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<UserTable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserTable::getUserName, username);
        UserTable userTable = userTableMapper.selectOne(wrapper);
        if (userTable == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        return new UserDetail(userTable);
    }


    private Boolean verifyUser(String userName){
        QueryWrapper<UserTable> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",userName);
        return userTableMapper.selectCount(wrapper) > 0;
    }

}
