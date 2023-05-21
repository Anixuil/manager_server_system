package com.anixuil.manager_system.service;

import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.StuTeachRelationTable;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anixuil
 * @since 2023-05-20
 */
public interface StuTeachRelationTableService extends IService<StuTeachRelationTable> {
    //获取与当前用户相关的关系
    Rest getStuTeachRelation(String token,Integer pageNum,Integer pageSize);

    //选择老师
    Rest chooseTeacher(String token,String teacherUuid);

    //教师同意
    Rest agree(String token,String relationUuid,String relationType);
}
