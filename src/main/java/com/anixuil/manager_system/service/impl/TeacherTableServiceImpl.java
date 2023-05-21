package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.CandidateTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.TeacherTable;
import com.anixuil.manager_system.mapper.TeacherTableMapper;
import com.anixuil.manager_system.pojo.UserAll;
import com.anixuil.manager_system.service.TeacherTableService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anixuil
 * @since 2023-03-09
 */
@Service
public class TeacherTableServiceImpl extends ServiceImpl<TeacherTableMapper, TeacherTable> implements TeacherTableService {

    @Override
    public Boolean addTeacher(UserAll user) {
        try{
            TeacherTable teacherTable = new TeacherTable();
            if(user.getTeacherId() == null){
                teacherTable.setTeacherId(String.valueOf(new Date().getTime()));
            }else{
                teacherTable.setTeacherId(user.getTeacherId());
            }
            teacherTable.setUserUuid(user.getUserUuid());
            teacherTable.setDepartUuid(user.getDepartUuid());
            teacherTable.setClassUuid(user.getClassUuid());
            teacherTable.setTeacherIntro(user.getTeacherIntro());
            return save(teacherTable);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean deleteTeacher(TeacherTable teacherTable) {
        try{
            LambdaQueryWrapper<TeacherTable> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TeacherTable::getUserUuid,teacherTable.getUserUuid());
            return remove(wrapper);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Rest updateTeacher(TeacherTable teacherTable) {
        String msg = "修改教师信息";
        try {
            boolean result = updateById(teacherTable);
            if(result) {
                return Rest.success(msg,true);
            }
            return Rest.fail(msg,false);
        }catch (Exception e) {
            return Rest.error(msg,e);
        }
    }

}
