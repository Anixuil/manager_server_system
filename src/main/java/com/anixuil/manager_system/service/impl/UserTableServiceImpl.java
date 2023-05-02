package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.*;
import com.anixuil.manager_system.mapper.UserTableMapper;
import com.anixuil.manager_system.pojo.UserAll;
import com.anixuil.manager_system.service.CandidateTableService;
import com.anixuil.manager_system.service.StudentTableService;
import com.anixuil.manager_system.service.TeacherTableService;
import com.anixuil.manager_system.service.UserTableService;
import com.anixuil.manager_system.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anixuil
 * @since 2023-03-09
 */
@Service
public class UserTableServiceImpl extends ServiceImpl<UserTableMapper, UserTable> implements UserTableService {

        @Resource
        private UserTableMapper userTableMapper;

        @Resource
        private AuthenticationManager authenticationManager;

        @Resource
        private CandidateTableService candidateTableService;

        @Resource
        private StudentTableService studentTableService;

        @Resource
        private TeacherTableService teacherTableService;

        //用户登录
        @Override
        public Rest login(UserTable userTable){
            String msg = "用户登录";
            try{
                //authentication 进行用户验证
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userTable.getUserName(), userTable.getUserPassword());
                Authentication authenticate = authenticationManager.authenticate(authenticationToken);
                if(Objects.isNull(authenticate)){
                    return Rest.fail(msg,"验证失败");
                }
//            如果认证通过了，使用userId生成一个Jwt
                UserDetail userDetail = (UserDetail) authenticate.getPrincipal();
                String token = JwtUtils.createJWT(userDetail.getUserUuid());
                Map<String, Object> map = new HashMap<>();
                map.put("token",token);
                return Rest.success(msg, map);
            }catch (Exception e){
                return Rest.error(e.getMessage(),e);
            }
        }

    @Override
    public Rest register(UserAll user) {
        String msg = "用户注册";
        try{
            //用户是否已经存在
            Boolean isExits = verifyUser(user);
            if(isExits){
                return Rest.fail(msg,"用户已经存在");
            }
            UserTable userTable = new UserTable();
            //密码加密
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            userTable.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
            userTable.setUserName(user.getUserName());
            userTable.setUserPhone(user.getUserPhone());
            userTable.setUserEmail(user.getUserEmail());
            userTable.setUserAge(user.getUserAge());
            userTable.setUserGender(user.getUserGender());
            if(user.getUserRole() == null) {
                userTable.setUserRole("candidate");
                user.setUserRole("candidate");
            }else{
                userTable.setUserRole(user.getUserRole());
            }
            boolean result = save(userTable);
            if(result){
                user.setUserUuid(userTable.getUserUuid());
                //注册成功后，创建一个默认的角色
                boolean can;
                switch (user.getUserRole()){
                    case "student":
                        can = studentTableService.addStudent(user);
                        break;
                    case "teacher":
                        can = teacherTableService.addTeacher(user);
                        break;
                    case "candidate":
                        can = candidateTableService.addCandidate(user);
                        break;
                    default:
                        can = true;
                        break;
                }
                if(can){
                    return Rest.success(msg, true);
                }
            }
            return Rest.fail(msg, false);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //获取当前用户信息
    @Override
    public Rest getUserInfo(String token) {
        String msg = "获取用户信息";
        try{
            JwtUtils jwtUtils = new JwtUtils();
            String userUuid = jwtUtils.parseJWT(token).getSubject();
            UserTable userTable = userTableMapper.selectOne(
                    new LambdaQueryWrapper<UserTable>().eq(UserTable::getUserUuid,userUuid)
            );
            //拿到用户的身份信息，然后根据身份信息去对应的表中查询
            String role = userTable.getUserRole();
            Map<String,Object> info = new HashMap<>();
            if(role.equals("candidate")){
                LambdaQueryWrapper<CandidateTable> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(CandidateTable::getUserUuid,userUuid);
                CandidateTable candidateTable = candidateTableService.getOne(wrapper);
                info.put("candidateUuid",candidateTable.getCandidateUuid());
                info.put("majorUuid",candidateTable.getMajorUuid());
                info.put("candidateId",candidateTable.getCandidateId());
                info.put("candidateStatus",candidateTable.getCandidateStatus());
                info.put("examPlace",candidateTable.getExamPlace());
            }
            if(role.equals("teacher")){
                LambdaQueryWrapper<TeacherTable> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(TeacherTable::getUserUuid,userUuid);
                TeacherTable teacherTable = teacherTableService.getOne(wrapper);
                info.put("teacherUuid",teacherTable.getTeacherUuid());
                info.put("teacherId",teacherTable.getTeacherId());
                info.put("departUuid",teacherTable.getDepartUuid());
                info.put("classUuid",teacherTable.getClassUuid());
                info.put("teacherIntro",teacherTable.getTeacherIntro());
            }
            if(role.equals("student")){
                LambdaQueryWrapper<StudentTable> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(StudentTable::getUserUuid,userUuid);
                StudentTable studentTable = studentTableService.getOne(wrapper);
                info.put("studentUuid",studentTable.getStudentUuid());
                info.put("studentId",studentTable.getStudentId());
                info.put("majorUuid",studentTable.getMajorUuid());
                info.put("entryDate",studentTable.getEntryDate());
                info.put("graduationDate",studentTable.getGraduationDate());
            }
            info.put("userUuid",userTable.getUserUuid());
            info.put("userName",userTable.getUserName());
            info.put("userEmail",userTable.getUserEmail());
            info.put("userPhone",userTable.getUserPhone());
            info.put("userRole",userTable.getUserRole());
            info.put("userAge",userTable.getUserAge());
            info.put("userGender",userTable.getUserGender());
            info.put("createDate",userTable.getCreateDate());
            info.put("updateDate",userTable.getUpdateDate());
            Map<String,Object> data = new HashMap<>();
            data.put("userInfo",info);
            if(userUuid != null){
                return Rest.success(msg,data);
            }
            return Rest.fail(msg,null);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //验证用户是否存在
//    @Override
    public Boolean verifyUser(UserTable userTable){
        QueryWrapper<UserTable> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",userTable.getUserName());
        return userTableMapper.selectCount(wrapper) > 0;
    }

//    @Override
    public Boolean verifyUser(UserAll user) {
        QueryWrapper<UserTable> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",user.getUserName());
        return userTableMapper.selectCount(wrapper) > 0;
    }

    //修改密码
    @Override
    public Rest updatePwd(UserTable userTable) {
        String msg = "修改密码";
        try{
            if(verifyUser(userTable)){
                String password = userTable.getUserPassword();
                //加密
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                password = bCryptPasswordEncoder.encode(password);
                userTable.setUserPassword(password);
                userTableMapper.update(userTable,new LambdaQueryWrapper<UserTable>().eq(UserTable::getUserUuid,userTable.getUserUuid()));
                return Rest.success(msg,null);
            }
            return Rest.fail(msg,null);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    @Override
    public Rest updateUserInfo(UserAll user) {
        String msg = "修改用户信息";
        UserTable userTable = new UserTable();
        userTable.setUserName(user.getUserName());
        userTable.setUserPhone(user.getUserPhone());
        userTable.setUserEmail(user.getUserEmail());
        userTable.setUserAge(user.getUserAge());
        userTable.setUserGender(user.getUserGender());
        userTable.setUserUuid(user.getUserUuid());
        userTable.setUserRole(user.getUserRole());
        String oldRole = getOne(new LambdaQueryWrapper<UserTable>().eq(UserTable::getUserUuid,user.getUserUuid()).select(UserTable::getUserRole)).getUserRole();
        try{
             //判断当前用户的角色是否有相关的身份
                String newRole = userTable.getUserRole();
                System.out.println("oldRole:"+oldRole);
                System.out.println("newRole:"+newRole);
                //如果角色没有改变，就不做处理
                if(!oldRole.equals(newRole)){
                    boolean delFlag = false;
                    boolean addFlag = false;
                    switch (oldRole) {
                        case "candidate": {
                            //如果是考生，就删除考生信息
                            LambdaQueryWrapper<CandidateTable> wrapper = new LambdaQueryWrapper<>();
                            wrapper.eq(CandidateTable::getUserUuid, userTable.getUserUuid());
                            delFlag = candidateTableService.remove(wrapper);
                            break;
                        }
                        case "student": {
                            //如果是学生，就删除学生信息
                            LambdaQueryWrapper<StudentTable> wrapper = new LambdaQueryWrapper<>();
                            wrapper.eq(StudentTable::getUserUuid, userTable.getUserUuid());
                            delFlag = studentTableService.remove(wrapper);
                            break;
                        }
                        case "teacher": {
                            //如果是教师，就删除教师信息
                            LambdaQueryWrapper<TeacherTable> wrapper = new LambdaQueryWrapper<>();
                            wrapper.eq(TeacherTable::getUserUuid, userTable.getUserUuid());
                            delFlag = teacherTableService.remove(wrapper);
                            break;
                        }
                    }
                    switch (newRole) {
                        case "candidate":
                            //如果是考生，就创建考生信息
                            addFlag = candidateTableService.addCandidate(user);
                            break;
                        case "student":
                            //如果是学生，就创建学生信息
                            addFlag = studentTableService.addStudent(user);
                            break;
                        case "teacher":
                            //如果是教师，就创建教师信息
                            addFlag = teacherTableService.addTeacher(user);
                            break;
                    }
                    System.out.println(delFlag);
                    System.out.println(addFlag);
                    if(addFlag || delFlag){
                        update(userTable,new LambdaQueryWrapper<UserTable>().eq(UserTable::getUserUuid,userTable.getUserUuid()));
                        return Rest.success(msg,true);
                    }
                }
            return Rest.fail(msg,null);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    @Override
    public Rest deleteUser(UserTable userTable) {
        String msg = "删除用户";
        try{
                String role = getOne(new LambdaQueryWrapper<UserTable>().eq(UserTable::getUserUuid,userTable.getUserUuid()).select(UserTable::getUserRole)).getUserRole();
                boolean flag;
                switch (role){
                    case "candidate":{
                        LambdaQueryWrapper<CandidateTable> wrapper = new LambdaQueryWrapper<>();
                        wrapper.eq(CandidateTable::getUserUuid,userTable.getUserUuid());
                        flag = candidateTableService.remove(wrapper);
                        break;
                    }
                    case "student":{
                        LambdaQueryWrapper<StudentTable> wrapper = new LambdaQueryWrapper<>();
                        wrapper.eq(StudentTable::getUserUuid,userTable.getUserUuid());
                        flag = studentTableService.remove(wrapper);
                        break;
                    }
                    case "teacher":{
                        LambdaQueryWrapper<TeacherTable> wrapper = new LambdaQueryWrapper<>();
                        wrapper.eq(TeacherTable::getUserUuid,userTable.getUserUuid());
                        flag = teacherTableService.remove(wrapper);
                        break;
                    }
                    default:{
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    boolean result = removeById(userTable);
                    if(result){
                        return Rest.success(msg,true);
                    }
                }
            return Rest.fail(msg,false);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }


}
