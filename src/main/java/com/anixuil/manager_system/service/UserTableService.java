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
    Rest updatePwd(String token, String oldPwd, String newPwd);

    //修改用户信息
    Rest updateUserInfo(UserAll user);

    //删除用户
    Rest deleteUser(UserTable userTable);

    //获取考生列表
    Rest getCandidateList(Integer pageNum,Integer pageSize,String userUuid,String userName,String userPhone,String userEmail,String majorUuid,String candidateId,String candidateStatus,String examPlace);

    //获取学生列表
    Rest getStudentList(Integer pageNum,Integer pageSize,String userUuid,String userName,String userPhone,String userEmail,String majorUuid,String studentId);

    //获取教师列表
    Rest getTeacherList(Integer pageNum,Integer pageSize,String userUuid,String userName,String userPhone,String userEmail,String teacherId,String departUuid,String classUuid);

    //修改考生信息
    Rest updateCandidate(UserAll user);

    //修改学生信息
    Rest updateStudent(UserAll user);

    //修改教师信息
    Rest updateTeacher(UserAll user);
}
