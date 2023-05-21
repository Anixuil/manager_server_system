package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.CandidateTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.StudentTable;
import com.anixuil.manager_system.mapper.StudentTableMapper;
import com.anixuil.manager_system.pojo.UserAll;
import com.anixuil.manager_system.service.StudentTableService;
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
public class StudentTableServiceImpl extends ServiceImpl<StudentTableMapper, StudentTable> implements StudentTableService {

    @Override
    public Boolean addStudent(UserAll user) {
        try{
            StudentTable studentTable = new StudentTable();
            if(user.getStudentId() == null) {
                studentTable.setStudentId(String.valueOf(new Date().getTime()));
            }else{
                studentTable.setStudentId(user.getStudentId());
            }
            studentTable.setUserUuid(user.getUserUuid());
            studentTable.setMajorUuid(user.getMajorUuid());
            studentTable.setEntryDate(user.getEntryDate());
            studentTable.setGraduationDate(user.getGraduationDate());
            return save(studentTable);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean deleteStudent(StudentTable studentTable) {
        try{
            LambdaQueryWrapper<StudentTable> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(StudentTable::getUserUuid,studentTable.getUserUuid());
            return remove(wrapper);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Rest updateStudent(StudentTable studentTable) {
        String msg = "修改学生信息";
        try {
            boolean result = updateById(studentTable);
            if(result) {
                return Rest.success(msg,true);
            }
            return Rest.fail(msg,false);
        }catch (Exception e) {
            return Rest.error(msg,e);
        }
    }

}
