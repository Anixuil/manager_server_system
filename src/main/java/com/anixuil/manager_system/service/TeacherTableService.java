package com.anixuil.manager_system.service;

import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.TeacherTable;
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
public interface TeacherTableService extends IService<TeacherTable> {
    //添加教师
    Boolean addTeacher(UserAll user);

    //删除教师
    Boolean deleteTeacher(TeacherTable teacherTable);

    //修改教师信息
    Rest updateTeacher(TeacherTable teacherTable);

}
