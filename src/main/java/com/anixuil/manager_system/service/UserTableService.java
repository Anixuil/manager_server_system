package com.anixuil.manager_system.service;

import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.UserTable;
import com.anixuil.manager_system.pojo.UserAll;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anixuil
 * @since 2023-03-09
 */
public interface UserTableService extends IService<UserTable> {

    //用户登录
    Rest login(UserTable userTable);

    //用户注册
    Rest register(UserAll user);

    //获取用户信息
    Rest getUserInfo(String token);

    //验证用户是否存在
    Boolean verifyUser(UserTable userTable);

    //修改密码
    Rest updatePwd(UserTable userTable);

    //修改用户信息
    Rest updateUserInfo(UserAll user);

    //删除用户
    Rest deleteUser(UserTable userTable);
}
