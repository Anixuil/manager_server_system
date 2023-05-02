package com.anixuil.manager_system.service;

import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.StudentTable;
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
public interface StudentTableService extends IService<StudentTable> {
    //添加学生
    Boolean addStudent(UserAll user);

    //删除学生
    Boolean deleteStudent(StudentTable studentTable);

    //修改学生信息
    Rest updateStudent(StudentTable studentTable);
}
