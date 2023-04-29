package com.anixuil.manager_system.service;

import com.anixuil.manager_system.entity.ClassTable;
import com.anixuil.manager_system.entity.Rest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anixuil
 * @since 2023-03-09
 */
public interface ClassTableService extends IService<ClassTable> {
    //添加课程
    Rest addClass(ClassTable classTable);

    //修改课程
    Rest updateClass(ClassTable classTable);

    //删除课程
    Rest deleteClass(ClassTable classTable);

    //获取课程列表
    Rest getClassList(Integer pageNum,Integer pageSize);
}
