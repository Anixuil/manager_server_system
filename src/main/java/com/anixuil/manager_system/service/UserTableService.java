package com.anixuil.manager_system.service;

import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.UserTable;
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
    Rest register(UserTable userTable);

    //获取用户信息
    Rest getUserInfo(String token);

    //验证用户是否存在
    Boolean verifyUser(UserTable userTable);

    //验证密码是否正确
    Boolean verifyPwd(UserTable userTable);
}
