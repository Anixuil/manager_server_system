package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.*;
import com.anixuil.manager_system.mapper.*;
import com.anixuil.manager_system.pojo.UserAll;
import com.anixuil.manager_system.service.*;
import com.anixuil.manager_system.utils.Datetime;
import com.anixuil.manager_system.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

        @Resource
        MajorTableService majorTableService;

        @Resource
        ExamScoreTableService examScoreTableService;
        @Resource
        ExamClassTableMapper examClassTableMapper;

        @Resource
        DepartTableService departTableService;

        @Resource
        ClassTableService classTableService;

        @Resource
        CandidateTableMapper candidateTableMapper;

        @Resource
        StudentTableMapper studentTableMapper;

        @Resource
        WorkFlowTableMapper workFlowTableMapper;

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
            UserTable userTable = new UserTable();
            //密码加密
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            userTable.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
            userTable.setUserName(user.getUserName());
            userTable.setUserPhone(user.getUserPhone());
            userTable.setUserEmail(user.getUserEmail());
            userTable.setUserAge(user.getUserAge());
            userTable.setUserGender(user.getUserGender());
            userTable.setUserHeadimg(user.getUserHeadimg());
            userTable.setUserAddress(user.getUserAddress());
            userTable.setUndergraduateSchool(user.getUndergraduateSchool());
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
                info.put("majorName",majorTableService.getById(candidateTable.getMajorUuid()).getMajorName());
                info.put("candidateId",candidateTable.getCandidateId());
                info.put("candidateStatus",candidateTable.getCandidateStatus());
                info.put("examPlace",candidateTable.getExamPlace());
                //查询考生初试成绩各科总成绩
                QueryWrapper<ExamScoreTable> examScoreTableQueryWrapper = new QueryWrapper<>();
                examScoreTableQueryWrapper.select("IFNULL(sum(exam_score),0) as firstScore").eq("user_uuid",userTable.getUserUuid()).eq("exam_type","0");
                Map<String,Object> examScoreMap = examScoreTableService.getMap(examScoreTableQueryWrapper);
                info.put("firstScore",examScoreMap.get("firstScore"));
                //查询考生复试成绩各科总成绩
                examScoreTableQueryWrapper = new QueryWrapper<>();
                examScoreTableQueryWrapper.select("IFNULL(sum(exam_score),0) as secondScore").eq("user_uuid",userTable.getUserUuid()).eq("exam_type","1");
                examScoreMap = examScoreTableService.getMap(examScoreTableQueryWrapper);
                info.put("secondScore",examScoreMap.get("secondScore"));
                //查询考生调剂成绩各科总成绩
                examScoreTableQueryWrapper = new QueryWrapper<>();
                examScoreTableQueryWrapper.select("IFNULL(sum(exam_score),0) as thirdScore").eq("user_uuid",userTable.getUserUuid()).eq("exam_type","2");
                examScoreMap = examScoreTableService.getMap(examScoreTableQueryWrapper);
                info.put("thirdScore",examScoreMap.get("thirdScore"));
                info.put("informationStatus",candidateTable.getInformationStatus());
                WorkFlowTable workFlowTable = workFlowTableMapper.selectOne(new LambdaQueryWrapper<WorkFlowTable>()
                        .select(WorkFlowTable::getWorkFlowDate)
                        .eq(WorkFlowTable::getWorkFlowType,candidateTable.getCandidateStatus())
                        .orderByDesc(WorkFlowTable::getWorkFlowDate)
                        .last("limit 1"));
                info.put("examDate",workFlowTable.getWorkFlowDate());
                System.out.println(candidateTable.getInformationStatus());
            }
            if(role.equals("teacher")){
                LambdaQueryWrapper<TeacherTable> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(TeacherTable::getUserUuid,userUuid);
                TeacherTable teacherTable = teacherTableService.getOne(wrapper);
                info.put("teacherUuid",teacherTable.getTeacherUuid());
                info.put("teacherId",teacherTable.getTeacherId());
                info.put("departUuid",teacherTable.getDepartUuid());
                info.put("departName",departTableService.getById(teacherTable.getDepartUuid()).getDepartName());
                info.put("classUuid",teacherTable.getClassUuid());
                info.put("className",classTableService.getById(teacherTable.getClassUuid()).getClassName());
                info.put("teacherIntro",teacherTable.getTeacherIntro());
            }
            if(role.equals("student")){
                LambdaQueryWrapper<StudentTable> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(StudentTable::getUserUuid,userUuid);
                StudentTable studentTable = studentTableService.getOne(wrapper);
                info.put("studentUuid",studentTable.getStudentUuid());
                info.put("studentId",studentTable.getStudentId());
                info.put("majorUuid",studentTable.getMajorUuid());
                info.put("majorName",majorTableService.getById(studentTable.getMajorUuid()).getMajorName());
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
            info.put("undergraduateSchool",userTable.getUndergraduateSchool());
            info.put("userHeadimg",userTable.getUserHeadimg());
            info.put("userAddress",userTable.getUserAddress());
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
    public Rest updatePwd(String token, String oldPwd, String newPwd) {
        String msg = "修改密码";
        try{
            JwtUtils jwtUtils = new JwtUtils();
            String userUuid = jwtUtils.parseJWT(token).getSubject();
            UserTable userTable = userTableMapper.selectOne(
                    new LambdaQueryWrapper<UserTable>().eq(UserTable::getUserUuid,userUuid)
            );
            //验证旧密码是否正确
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if(bCryptPasswordEncoder.matches(oldPwd,userTable.getUserPassword())){
                //加密
                newPwd = bCryptPasswordEncoder.encode(newPwd);
                userTable.setUserPassword(newPwd);
                userTableMapper.update(userTable,new LambdaQueryWrapper<UserTable>().eq(UserTable::getUserUuid,userUuid));
                return Rest.success(msg,null);
            }else{
                return Rest.fail(msg,"旧密码错误");
            }
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
        userTable.setUndergraduateSchool(user.getUndergraduateSchool());
        userTable.setUserAddress(user.getUserAddress());
        userTable.setUserHeadimg(user.getUserHeadimg());
        String oldRole = getOne(new LambdaQueryWrapper<UserTable>().eq(UserTable::getUserUuid,user.getUserUuid()).select(UserTable::getUserRole)).getUserRole();
        try{
             //判断当前用户的角色是否有相关的身份
                String newRole = userTable.getUserRole();
                //如果角色没有改变，就不做处理
                if(!oldRole.equals(newRole)){
                    switch (oldRole) {
                        case "candidate": {
//                            //如果是考生，就删除考生信息
                            if(newRole.equals("teacher")){
                                LambdaQueryWrapper<CandidateTable> wrapper = new LambdaQueryWrapper<>();
                                wrapper.eq(CandidateTable::getUserUuid, userTable.getUserUuid());
                                candidateTableService.remove(wrapper);
                            }
                            break;
                        }
                        case "student": {
                            if(newRole.equals("teacher")){
                                LambdaQueryWrapper<StudentTable> wrapper = new LambdaQueryWrapper<>();
                                wrapper.eq(StudentTable::getUserUuid, userTable.getUserUuid());
                                studentTableService.remove(wrapper);
                            }
                            break;
                        }
                        case "teacher": {
                            //如果是教师，就删除教师信息
                            LambdaQueryWrapper<TeacherTable> wrapper = new LambdaQueryWrapper<>();
                            wrapper.eq(TeacherTable::getUserUuid, userTable.getUserUuid());
                            teacherTableService.remove(wrapper);
                            break;
                        }
                    }
                    switch (newRole) {
                        case "candidate":
                            //如果是考生，就创建考生信息
                            LambdaQueryWrapper<CandidateTable> wrapper = new LambdaQueryWrapper<>();
                            wrapper.eq(CandidateTable::getUserUuid, userTable.getUserUuid());
                            if(!candidateTableMapper.exists(wrapper)){
                                candidateTableService.addCandidate(user);
                            }
                            break;
                        case "student":
                            LambdaQueryWrapper<StudentTable> wrapper1 = new LambdaQueryWrapper<>();
                            wrapper1.eq(StudentTable::getUserUuid, userTable.getUserUuid());
                            System.out.println(!studentTableMapper.exists(wrapper1));
                            if(!studentTableMapper.exists(wrapper1)){
                                System.out.println("添加学生信息");
                                studentTableService.addStudent(user);
                            }
                            break;
                        case "teacher":
                            //如果是教师，就创建教师信息
                            teacherTableService.addTeacher(user);
                            break;
                    }

                        update(userTable,new LambdaQueryWrapper<UserTable>().eq(UserTable::getUserUuid,userTable.getUserUuid()));
                        return Rest.success(msg,true);
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

    @Override
    public Rest getCandidateList(Integer pageNum, Integer pageSize, String userUuid, String userName, String userPhone, String userEmail, String majorUuid, String candidateId, String candidateStatus, String examPlace) {
        String msg = "获取考生列表";
        try{
            IPage<UserTable> page = new Page<>(pageNum,pageSize);
            MPJLambdaWrapper<UserTable> wrapper = new MPJLambdaWrapper<>();
            wrapper.selectAll(UserTable.class)
                    .like(UserTable::getUserUuid,userUuid)
                    .like(UserTable::getUserName,userName)
                    .like(UserTable::getUserPhone,userPhone)
                    .like(UserTable::getUserEmail,userEmail)
                    .leftJoin(CandidateTable.class,CandidateTable::getUserUuid,UserTable::getUserUuid)
                    .select(CandidateTable::getCandidateUuid,CandidateTable::getCandidateId,CandidateTable::getCandidateStatus,CandidateTable::getExamPlace,CandidateTable::getMajorUuid,CandidateTable::getInformationStatus,CandidateTable::getExamDate)
                    .like(CandidateTable::getMajorUuid,majorUuid)
                    .like(CandidateTable::getCandidateId,candidateId)
                    .like(CandidateTable::getCandidateStatus,candidateStatus)
                    .like(CandidateTable::getExamPlace,examPlace);
            IPage<UserAll> userAllIPage = baseMapper.selectJoinPage(page, UserAll.class, wrapper);
            List<UserAll> userAllList = userAllIPage.getRecords();
            List<Map<String,Object>> mapList = userAllList.stream().map(userAll -> {
                Map<String,Object> map = new HashMap<>();
                map.put("userUuid",userAll.getUserUuid());
                map.put("userName",userAll.getUserName());
                map.put("userPhone",userAll.getUserPhone());
                map.put("userEmail",userAll.getUserEmail());
                map.put("userRole",userAll.getUserRole());
                map.put("userGender",userAll.getUserGender());
                map.put("userAge",userAll.getUserAge());
                map.put("userAddress",userAll.getUserAddress());
                map.put("userHeadimg",userAll.getUserHeadimg());
                map.put("majorUuid",userAll.getMajorUuid());
                map.put("majorName",majorTableService.getById(userAll.getMajorUuid()).getMajorName());
                map.put("candidateUuid",userAll.getCandidateUuid());
                map.put("candidateId",userAll.getCandidateId());
                map.put("candidateStatus",userAll.getCandidateStatus());
                map.put("examPlace",userAll.getExamPlace());
                WorkFlowTable workFlowTable = workFlowTableMapper.selectOne(new LambdaQueryWrapper<WorkFlowTable>()
                        .select(WorkFlowTable::getWorkFlowDate)
                        .eq(WorkFlowTable::getWorkFlowType,userAll.getCandidateStatus())
                        .orderByDesc(WorkFlowTable::getWorkFlowDate)
                        .last("limit 1"));
                map.put("examDate",workFlowTable.getWorkFlowDate());
                map.put("undergraduateSchool",userAll.getUndergraduateSchool());
                map.put("createDate", Datetime.format(userAll.getCreateDate()));
                map.put("updateDate",Datetime.format(userAll.getUpdateDate()));
                //查询考生初试成绩各科总成绩
                QueryWrapper<ExamScoreTable> examScoreTableQueryWrapper = new QueryWrapper<>();
                examScoreTableQueryWrapper.select("IFNULL(sum(exam_score),0) as firstScore").eq("user_uuid",userAll.getUserUuid()).eq("exam_type","0");
                Map<String,Object> examScoreMap = examScoreTableService.getMap(examScoreTableQueryWrapper);
                map.put("firstScore",examScoreMap.get("firstScore"));
                //查询考生复试成绩各科总成绩
                examScoreTableQueryWrapper = new QueryWrapper<>();
                examScoreTableQueryWrapper.select("IFNULL(sum(exam_score),0) as secondScore").eq("user_uuid",userAll.getUserUuid()).eq("exam_type","1");
                examScoreMap = examScoreTableService.getMap(examScoreTableQueryWrapper);
                map.put("secondScore",examScoreMap.get("secondScore"));
                //查询考生调剂成绩各科总成绩
                examScoreTableQueryWrapper = new QueryWrapper<>();
                examScoreTableQueryWrapper.select("IFNULL(sum(exam_score),0) as thirdScore").eq("user_uuid",userAll.getUserUuid()).eq("exam_type","2");
                examScoreMap = examScoreTableService.getMap(examScoreTableQueryWrapper);
                map.put("thirdScore",examScoreMap.get("thirdScore"));
                map.put("informationStatus",userAll.getInformationStatus());
                return map;
            }).collect(Collectors.toList());
            Map<String,Object> map = new HashMap<>();
            map.put("total",userAllIPage.getTotal());
            map.put("records",mapList);
            map.put("pageSize",userAllIPage.getSize());
            map.put("currentPage",userAllIPage.getCurrent());
            map.put("pages",userAllIPage.getPages());
            return Rest.success(msg,map);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //修改考生信息
    @Override
    public Rest updateCandidate(UserAll userAll) {
        String msg = "修改考生信息";
        try{
            UserTable userTable = new UserTable();
            userTable.setUserUuid(userAll.getUserUuid());
            userTable.setUserName(userAll.getUserName());
            userTable.setUserPhone(userAll.getUserPhone());
            userTable.setUserEmail(userAll.getUserEmail());
            userTable.setUserGender(userAll.getUserGender());
            userTable.setUserRole(userAll.getUserRole());
            userTable.setUndergraduateSchool(userAll.getUndergraduateSchool());
            userTable.setUserHeadimg(userAll.getUserHeadimg());
            userTable.setUserAddress(userAll.getUserAddress());
            boolean result = updateById(userTable);
            if(result){
                CandidateTable candidateTable = new CandidateTable();
                candidateTable.setUserUuid(userAll.getUserUuid());
                candidateTable.setCandidateUuid(userAll.getCandidateUuid());
                candidateTable.setMajorUuid(userAll.getMajorUuid());
                candidateTable.setCandidateId(userAll.getCandidateId());
                candidateTable.setCandidateStatus(userAll.getCandidateStatus());
                candidateTable.setExamPlace(userAll.getExamPlace());
                candidateTable.setExamDate(userAll.getExamDate());
                candidateTable.setInformationStatus(userAll.getInformationStatus());
                //如果考生的考试状态与数据库的不同，则覆盖
                if(!userAll.getCandidateStatus().equals(candidateTableService.getById(userAll.getCandidateUuid()).getCandidateStatus())){
                    //根据变化的考生状态，修改考生informationStatus
                    switch (candidateTable.getCandidateStatus()){
                        case "0":
                            candidateTable.setInformationStatus("00");
                            break;
                        case "1":
                            candidateTable.setInformationStatus("10");
                            break;
                        case "2":
                            candidateTable.setInformationStatus("20");
                            break;
                        case "3":
                            candidateTable.setInformationStatus("30");
                        case "4":
                            candidateTable.setInformationStatus("40");
                            break;
                    }
                }

                result = candidateTableService.updateById(candidateTable);
                if(result){
                    return Rest.success(msg,true);
                }
            }
            return Rest.fail(msg,false);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //获取学生列表
    @Override
    public Rest getStudentList(Integer pageNum, Integer pageSize, String userUuid, String userName, String userPhone, String userEmail, String majorUuid, String studentId) {
        String msg = "获取学生列表";
        try{
            IPage<UserTable> page = new Page<>(pageNum,pageSize);
            MPJLambdaWrapper<UserTable> wrapper = new MPJLambdaWrapper<>();
            wrapper.selectAll(UserTable.class)
                    .like(UserTable::getUserUuid,userUuid)
                    .like(UserTable::getUserName,userName)
                    .like(UserTable::getUserPhone,userPhone)
                    .like(UserTable::getUserEmail,userEmail)
                    .leftJoin(StudentTable.class,StudentTable::getUserUuid,UserTable::getUserUuid)
                    .select(StudentTable::getStudentUuid,StudentTable::getStudentId,StudentTable::getMajorUuid,StudentTable::getEntryDate,StudentTable::getGraduationDate)
                    .like(StudentTable::getStudentId,studentId)
                    .like(StudentTable::getMajorUuid,majorUuid);
            IPage<UserAll> userAllIPage = baseMapper.selectJoinPage(page, UserAll.class, wrapper);
            List<UserAll> userAllList = userAllIPage.getRecords();
            List<Map<String,Object>> mapList = userAllList.stream().map(userAll -> {
                Map<String,Object> map = new HashMap<>();
                map.put("userUuid",userAll.getUserUuid());
                map.put("userName",userAll.getUserName());
                map.put("userPhone",userAll.getUserPhone());
                map.put("userEmail",userAll.getUserEmail());
                map.put("userRole",userAll.getUserRole());
                map.put("userGender",userAll.getUserGender());
                map.put("userHeadimg",userAll.getUserHeadimg());
                map.put("userAddress",userAll.getUserAddress());
                map.put("userAge",userAll.getUserAge());
                map.put("undergraduateSchool",userAll.getUndergraduateSchool());
                map.put("majorUuid",userAll.getMajorUuid());
                map.put("majorName",majorTableService.getById(userAll.getMajorUuid()).getMajorName());
                map.put("studentUuid",userAll.getStudentUuid());
                map.put("studentId",userAll.getStudentId());
                map.put("entryDate",userAll.getEntryDate());
                map.put("graduationDate",userAll.getGraduationDate());
                map.put("createDate", Datetime.format(userAll.getCreateDate()));
                map.put("updateDate",Datetime.format(userAll.getUpdateDate()));
                return map;
            }).collect(Collectors.toList());
            Map<String,Object> map = new HashMap<>();
            map.put("total",userAllIPage.getTotal());
            map.put("records",mapList);
            map.put("pageSize",userAllIPage.getSize());
            map.put("currentPage",userAllIPage.getCurrent());
            map.put("pages",userAllIPage.getPages());
            return Rest.success(msg,map);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //修改学生信息
    @Override
    public Rest updateStudent(UserAll userAll) {
        String msg = "修改学生信息";
        try {
            UserTable userTable = new UserTable();
            userTable.setUserUuid(userAll.getUserUuid());
            userTable.setUserName(userAll.getUserName());
            userTable.setUserPhone(userAll.getUserPhone());
            userTable.setUserEmail(userAll.getUserEmail());
            userTable.setUserGender(userAll.getUserGender());
            userTable.setUserRole(userAll.getUserRole());
            userTable.setUserHeadimg(userAll.getUserHeadimg());
            userTable.setUserAddress(userAll.getUserAddress());
            userTable.setUndergraduateSchool(userAll.getUndergraduateSchool());
            boolean result = updateById(userTable);
            if (result) {
                StudentTable studentTable = new StudentTable();
                studentTable.setUserUuid(userAll.getUserUuid());
                studentTable.setStudentUuid(userAll.getStudentUuid());
                studentTable.setMajorUuid(userAll.getMajorUuid());
                studentTable.setStudentId(userAll.getStudentId());
                studentTable.setEntryDate(userAll.getEntryDate());
                studentTable.setGraduationDate(userAll.getGraduationDate());
                result = studentTableService.updateById(studentTable);
                if (result) {
                    return Rest.success(msg, true);
                }
            }
            return Rest.fail(msg, false);
        } catch (Exception e) {
            return Rest.error(msg, e);
        }
    }

    //获取教师列表
    @Override
    public Rest getTeacherList(Integer pageNum, Integer pageSize, String userUuid, String userName, String userPhone, String userEmail, String teacherId, String departUuid, String classUuid) {
        String msg = "获取教师列表";
        try{
            IPage<UserTable> page = new Page<>(pageNum,pageSize);
            MPJLambdaWrapper<UserTable> wrapper = new MPJLambdaWrapper<>();
            wrapper.selectAll(UserTable.class)
                    .like(UserTable::getUserUuid,userUuid)
                    .like(UserTable::getUserName,userName)
                    .like(UserTable::getUserPhone,userPhone)
                    .like(UserTable::getUserEmail,userEmail)
                    .leftJoin(TeacherTable.class,TeacherTable::getUserUuid,UserTable::getUserUuid)
                    .select(TeacherTable::getTeacherUuid,TeacherTable::getTeacherId,TeacherTable::getTeacherIntro,TeacherTable::getDepartUuid,TeacherTable::getClassUuid)
                    .like(TeacherTable::getTeacherId,teacherId)
                    .like(TeacherTable::getDepartUuid,departUuid)
                    .like(TeacherTable::getClassUuid,classUuid);
            IPage<UserAll> userAllIPage = baseMapper.selectJoinPage(page, UserAll.class, wrapper);
            List<UserAll> userAllList = userAllIPage.getRecords();
            List<Map<String,Object>> mapList = userAllList.stream().map(userAll -> {
                Map<String,Object> map = new HashMap<>();
                map.put("userUuid",userAll.getUserUuid());
                map.put("userName",userAll.getUserName());
                map.put("userPhone",userAll.getUserPhone());
                map.put("userEmail",userAll.getUserEmail());
                map.put("userRole",userAll.getUserRole());
                map.put("userGender",userAll.getUserGender());
                map.put("undergraduateSchool",userAll.getUndergraduateSchool());
                map.put("userHeadimg",userAll.getUserHeadimg());
                map.put("userAddress",userAll.getUserAddress());
                map.put("userAge",userAll.getUserAge());
                map.put("teacherUuid",userAll.getTeacherUuid());
                map.put("teacherId",userAll.getTeacherId());
                map.put("teacherIntro",userAll.getTeacherIntro());
                map.put("classUuid",userAll.getClassUuid());
                map.put("departUuid",userAll.getDepartUuid());
                map.put("departName",departTableService.getById(userAll.getDepartUuid()).getDepartName());
                map.put("className",classTableService.getById(userAll.getClassUuid()).getClassName());
                map.put("createDate", Datetime.format(userAll.getCreateDate()));
                map.put("updateDate",Datetime.format(userAll.getUpdateDate()));
                return map;
            }).collect(Collectors.toList());
            Map<String,Object> map = new HashMap<>();
            map.put("total",userAllIPage.getTotal());
            map.put("records",mapList);
            map.put("pageSize",userAllIPage.getSize());
            map.put("currentPage",userAllIPage.getCurrent());
            map.put("pages",userAllIPage.getPages());
            return Rest.success(msg,map);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //修改教师信息
    @Override
    public Rest updateTeacher(UserAll userAll) {
        String msg = "修改教师信息";
        try {
            UserTable userTable = new UserTable();
            userTable.setUserUuid(userAll.getUserUuid());
            userTable.setUserName(userAll.getUserName());
            userTable.setUserPhone(userAll.getUserPhone());
            userTable.setUserEmail(userAll.getUserEmail());
            userTable.setUserGender(userAll.getUserGender());
            userTable.setUserRole(userAll.getUserRole());
            userTable.setUserHeadimg(userAll.getUserHeadimg());
            userTable.setUserAddress(userAll.getUserAddress());
            userTable.setUndergraduateSchool(userAll.getUndergraduateSchool());
            boolean result = updateById(userTable);
            if (result) {
                TeacherTable teacherTable = new TeacherTable();
                teacherTable.setUserUuid(userAll.getUserUuid());
                teacherTable.setTeacherUuid(userAll.getTeacherUuid());
                teacherTable.setTeacherId(userAll.getTeacherId());
                teacherTable.setTeacherIntro(userAll.getTeacherIntro());
                teacherTable.setDepartUuid(userAll.getDepartUuid());
                teacherTable.setClassUuid(userAll.getClassUuid());
                result = teacherTableService.updateById(teacherTable);
                if (result) {
                    return Rest.success(msg, true);
                }
            }
            return Rest.fail(msg, false);
        } catch (Exception e) {
            return Rest.error(msg, e);
        }
    }

}
