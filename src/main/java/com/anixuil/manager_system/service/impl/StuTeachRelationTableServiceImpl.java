package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.*;
import com.anixuil.manager_system.mapper.*;
import com.anixuil.manager_system.pojo.UserAll;
import com.anixuil.manager_system.service.StuTeachRelationTableService;
import com.anixuil.manager_system.service.TeacherTableService;
import com.anixuil.manager_system.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anixuil
 * @since 2023-05-20
 */
@Service
public class StuTeachRelationTableServiceImpl extends ServiceImpl<StuTeachRelationTableMapper, StuTeachRelationTable> implements StuTeachRelationTableService {
    @Resource
    UserTableMapper userTableMapper;
    @Resource
    TeacherTableMapper teacherTableMapper;
    @Resource
    TeacherTableService teacherTableService;
    @Resource
    DepartTableMapper departTableMapper;
    @Resource
    ClassTableMapper classTableMapper;
    @Resource
    CandidateTableMapper candidateTableMapper;

    //获取与当前用户相关的关系
    @Override
    public Rest getStuTeachRelation(String token, Integer pageNum, Integer pageSize) {
        String msg = "获取与当前用户相关的关系";
        try{
            //解析token里蕴含的信息
            String userUuid = JwtUtils.parseJWT(token).getSubject();
            UserTable userTable = userTableMapper.selectOne(
                    new LambdaQueryWrapper<UserTable>().eq(UserTable::getUserUuid,userUuid)
            );
            //如果用户不存在
            if(userTable == null){
                return Rest.error(msg,false);
            }
            //用户存在，根据用户身份的不同去查询且返回对应的数据
            Map<String,Object> result = new HashMap<>();
            switch (userTable.getUserRole()){
                case "admin":
                    //查询所有的学生与教师的关系
                    IPage<TeacherTable> page = new Page<>(pageNum,pageSize);
                    IPage<TeacherTable> teacherTableIPage = teacherTableService.page(page);
                    List<TeacherTable> teacherTableList = teacherTableIPage.getRecords();
                    List<Map<String,Object>> mapList = teacherTableList.stream().map(teacherTable -> {
                        Map<String,Object> map = new HashMap<>();
                        //我们要拿到教师的信息去关系表里匹配生成属性结构的数据
                        UserTable userTable1 = userTableMapper.selectOne(
                                new LambdaQueryWrapper<UserTable>().eq(UserTable::getUserUuid,teacherTable.getUserUuid())
                        );
                        map.put("userName",userTable1.getUserName());
                        map.put("userUuid",userTable1.getUserUuid());
                        map.put("userRole",userTable1.getUserRole());
                        map.put("userPhone",userTable1.getUserPhone());
                        map.put("userEmail",userTable1.getUserEmail());
                        map.put("userGender",userTable1.getUserGender());
                        map.put("userAge",userTable1.getUserAge());
                        map.put("undergraduateSchool",userTable1.getUndergraduateSchool());
                        //然后看关系表里是否有对应的关系
                        List<StuTeachRelationTable> stuTeachRelationTableList = list(
                                new LambdaQueryWrapper<StuTeachRelationTable>().eq(StuTeachRelationTable::getTeachUserUuid,userTable1.getUserUuid())
                        );
                        //如果有关系，就把关系里的学生信息也拿出来
                        if(stuTeachRelationTableList.size() > 0){
                            List<Map<String,Object>> mapList1 = stuTeachRelationTableList.stream().map(stuTeachRelationTable -> {
                                Map<String,Object> map1 = new HashMap<>();
                                UserTable userTable2 = userTableMapper.selectOne(
                                        new LambdaQueryWrapper<UserTable>().eq(UserTable::getUserUuid,stuTeachRelationTable.getStuUserUuid())
                                );
                                map1.put("userName",userTable2.getUserName());
                                map1.put("userUuid",userTable2.getUserUuid());
                                map1.put("userRole",userTable2.getUserRole());
                                map1.put("userPhone",userTable2.getUserPhone());
                                map1.put("userEmail",userTable2.getUserEmail());
                                map1.put("userGender",userTable2.getUserGender());
                                map1.put("userAge",userTable2.getUserAge());
                                map1.put("parentUuid",userTable1.getUserUuid());
                                map1.put("undergraduateSchool",userTable2.getUndergraduateSchool());
                                return map1;
                            }).collect(Collectors.toList());
                            map.put("children",mapList1);
                        }
                        return map;
                    }).collect(Collectors.toList());
                    result.put("total",teacherTableIPage.getTotal());
                    result.put("records",mapList);
                    result.put("pageSize",teacherTableIPage.getSize());
                    result.put("pages",teacherTableIPage.getPages());
                    result.put("currentPage",teacherTableIPage.getCurrent());
                    break;
                case "student":
                case "candidate":
                    //查询当前学生的所有关系
                    StuTeachRelationTable stuTeachRelationTable = getOne(
                            new LambdaQueryWrapper<StuTeachRelationTable>().eq(StuTeachRelationTable::getStuUserUuid,userTable.getUserUuid())
                                    .notLike(StuTeachRelationTable::getRelationType,"2")
                    );
                    //如果没有关系
                    if(stuTeachRelationTable == null){
                        return Rest.success(msg,result);
                    }
                    //如果有关系 判断是否是拒绝关系
                    if(stuTeachRelationTable.getRelationType().equals("2")){
                        return Rest.success(msg,result);
                    }
                    //取出教师信息
                    UserTable userTable1 = userTableMapper.selectOne(
                            new LambdaQueryWrapper<UserTable>().eq(UserTable::getUserUuid,stuTeachRelationTable.getTeachUserUuid())
                    );
                    Map<String,Object> map = new HashMap<>();
                    map.put("userName",userTable1.getUserName());
                    map.put("userUuid",userTable1.getUserUuid());
                    map.put("userRole",userTable1.getUserRole());
                    map.put("userPhone",userTable1.getUserPhone());
                    map.put("userEmail",userTable1.getUserEmail());
                    map.put("userGender",userTable1.getUserGender());
                    map.put("userAge",userTable1.getUserAge());
                    map.put("userHeadimg",userTable1.getUserHeadimg());
                    map.put("undergraduateSchool",userTable1.getUndergraduateSchool());
                    TeacherTable teacherTable = teacherTableMapper.selectOne(
                            new LambdaQueryWrapper<TeacherTable>().eq(TeacherTable::getUserUuid,userTable1.getUserUuid())
                    );
                    map.put("teacheId",teacherTable.getTeacherId());
                    map.put("departUuid",teacherTable.getDepartUuid());
                    map.put("departName",departTableMapper.selectOne(
                            new LambdaQueryWrapper<DepartTable>().eq(DepartTable::getDepartUuid,teacherTable.getDepartUuid())
                    ).getDepartName());
                    map.put("classUuid",teacherTable.getClassUuid());
                    map.put("className",classTableMapper.selectOne(
                            new LambdaQueryWrapper<ClassTable>().eq(ClassTable::getClassUuid,teacherTable.getClassUuid())
                    ).getClassName());
                    map.put("teacherIntro",teacherTable.getTeacherIntro());
                    map.put("relationType",stuTeachRelationTable.getRelationType());
                    map.put("relationUuid",stuTeachRelationTable.getRelationUuid());
                    result.put("teacherData",map);
                    break;
                //如果没有关系
                    //取出教师信息
                case "teacher":
                    IPage<StuTeachRelationTable> teacherPage = new Page<>(pageNum,pageSize);
                    LambdaQueryWrapper<StuTeachRelationTable> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(StuTeachRelationTable::getTeachUserUuid,userTable.getUserUuid());
                    IPage<StuTeachRelationTable> stuTeachRelationTableIPage = page(teacherPage, queryWrapper);
                    List<StuTeachRelationTable> stuTeachRelationTableList = stuTeachRelationTableIPage.getRecords();
                    List<Map<String,Object>> mapList1 = stuTeachRelationTableList.stream().map(stuTeachRelationTable2 -> {
                        Map<String,Object> map2 = new HashMap<>();
                        UserTable userTable3 = userTableMapper.selectOne(
                                new LambdaQueryWrapper<UserTable>().eq(UserTable::getUserUuid,stuTeachRelationTable2.getStuUserUuid())
                        );
                        map2.put("userName",userTable3.getUserName());
                        map2.put("userUuid",userTable3.getUserUuid());
                        map2.put("userRole",userTable3.getUserRole());
                        map2.put("userPhone",userTable3.getUserPhone());
                        map2.put("userEmail",userTable3.getUserEmail());
                        map2.put("userGender",userTable3.getUserGender());
                        map2.put("userAge",userTable3.getUserAge());
                        map2.put("userHeadimg",userTable3.getUserHeadimg());
                        map2.put("undergraduateSchool",userTable3.getUndergraduateSchool());
                        map2.put("relationType",stuTeachRelationTable2.getRelationType());
                        map2.put("relationUuid",stuTeachRelationTable2.getRelationUuid());
                        return map2;
                    }).collect(Collectors.toList());
                    result.put("total",stuTeachRelationTableIPage.getTotal());
                    result.put("records",mapList1);
                    result.put("pageSize",stuTeachRelationTableIPage.getSize());
                    result.put("pages",stuTeachRelationTableIPage.getPages());
                    result.put("currentPage",stuTeachRelationTableIPage.getCurrent());
                    break;
            }
            return Rest.success(msg,result);
        }catch (Exception e){
            e.printStackTrace();
            return Rest.error(msg,false);
        }
    }

    //选择教师
    @Override
    public Rest chooseTeacher(String token,String teacherUuid){
        String msg = "选择教师";
        try{
            //解析token里蕴含的信息
            String userUuid = JwtUtils.parseJWT(token).getSubject();
            UserTable userTable = userTableMapper.selectOne(
                    new LambdaQueryWrapper<UserTable>().eq(UserTable::getUserUuid,userUuid)
            );
            //判断当前用户是否是学生或考生
            if(userTable.getUserRole().equals("admin") || userTable.getUserRole().equals("teacher")){
                return Rest.error(msg,"当前用户不是学生");
            }
            //判断当前学生是否已经选择了教师
            StuTeachRelationTable stuTeachRelationTable = getOne(
                    new LambdaQueryWrapper<StuTeachRelationTable>().eq(StuTeachRelationTable::getStuUserUuid,userUuid)
            );
            if(stuTeachRelationTable != null){
                return Rest.error(msg,"当前学生已经选择了教师");
            }
            //判断当前教师是否存在
            TeacherTable teacherTable = teacherTableMapper.selectOne(
                    new LambdaQueryWrapper<TeacherTable>().eq(TeacherTable::getTeacherUuid,teacherUuid)
            );
            StuTeachRelationTable stuTeachRelationTable1 = new StuTeachRelationTable();
            stuTeachRelationTable1.setStuUserUuid(userUuid);
            stuTeachRelationTable1.setTeachUserUuid(teacherTable.getUserUuid());
            save(stuTeachRelationTable1);
            return Rest.success(msg,true);
        }catch (Exception e){
            e.printStackTrace();
            return Rest.error(msg,false);
        }
    }

    //教师同意
    @Override
    public Rest agree(String token,String relationUuid,String relationType){
        String msg = "审批";
        try{
            //解析token里蕴含的信息
            String userUuid = JwtUtils.parseJWT(token).getSubject();
            UserTable userTable = userTableMapper.selectOne(
                    new LambdaQueryWrapper<UserTable>().eq(UserTable::getUserUuid,userUuid)
            );
            //判断当前用户是否是教师
            if(!userTable.getUserRole().equals("teacher")){
                return Rest.error(msg,"当前用户不是教师");
            }
            //判断当前关系是否存在
            StuTeachRelationTable stuTeachRelationTable = getOne(
                    new LambdaQueryWrapper<StuTeachRelationTable>().eq(StuTeachRelationTable::getRelationUuid,relationUuid)
            );
            if(stuTeachRelationTable == null){
                return Rest.error(msg,"当前关系不存在");
            }
            stuTeachRelationTable.setRelationType(relationType);
            updateById(stuTeachRelationTable);
            if(relationType.equals("1")) {
                return Rest.success(msg, true);
            }
            return Rest.success(msg+"拒绝",true);
        }catch (Exception e){
            e.printStackTrace();
            return Rest.error(msg,false);
        }
    }
}
