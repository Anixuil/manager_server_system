package com.anixuil.manager_system.controller;

import com.alibaba.excel.EasyExcel;
import com.anixuil.manager_system.entity.*;
import com.anixuil.manager_system.mapper.*;
import com.anixuil.manager_system.pojo.DepartAll;
import com.anixuil.manager_system.pojo.UserAll;
import com.anixuil.manager_system.pojo.UserScoreAll;
import com.anixuil.manager_system.service.*;
import com.anixuil.manager_system.service.impl.UserTableServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("publicfile")
public class uploadController {

    @Resource
    DepartTableMapper departTableMapper;
    @Resource
    UserTableMapper userTableMapper;
    @Resource
    CandidateTableMapper candidateTableMapper;
    @Resource
    ClassTableMapper classTableMapper;
    @Resource
    ExamClassTableService examClassTableService;
    @Resource
    UserTableService userTableService;
    @Resource
    MajorTableMapper majorTableMapper;
    @Resource
    StudentTableMapper studentTableMapper;
    @Resource
    TeacherTableMapper teacherTableMapper;
    @Resource
    ExamScoreTableMapper examScoreTableMapper;
    @Resource
    ExamClassTableMapper examClassTableMapper;

    //excel表格导入
    @PostMapping("importExcel")
    public Rest importExcel(@RequestBody MultipartFile file,String className){
        String msg = "导入excel表格";
        try{
            if(file != null){
                List<String> successList = new ArrayList();
                List<String> failList = new ArrayList();
                List<String> existList = new ArrayList();
                Map<String,Object> result = new HashMap<>();
                switch (className){
                    case "DepartTable":
                        List<DepartAll> departData = new ArrayList<>();
                        departData = EasyExcel.read(file.getInputStream())
                                .head(DepartAll.class)
                                .sheet()
                                .headRowNumber(1)
                                .doReadSync();
                        for(DepartAll item : departData){
                            if(item.getDepartName() == null){
                                return Rest.fail(msg,"导入失败，院系名称不能为空");
                            }
                            //如果院系存在
                            LambdaQueryWrapper<DepartTable> departWrapper = new LambdaQueryWrapper<>();
                            departWrapper.eq(DepartTable::getDepartName,item.getDepartName());
                            if(item.getDepartName() != null && departTableMapper.exists(departWrapper)){
                                System.out.println("院系已存在");
                                DepartTable departTable = departTableMapper.selectOne(departWrapper);
                                LambdaQueryWrapper<MajorTable> majorWrapper = new LambdaQueryWrapper<>();
                                majorWrapper
                                        .eq(MajorTable::getDepartUuid,departTable.getDepartUuid())
                                        .eq(MajorTable::getMajorName,item.getMajorName());
                                MajorTable majorTable = majorTableMapper.selectOne(majorWrapper);
                                if(item.getMajorName() != null && majorTableMapper.exists(majorWrapper)){
                                    System.out.println("专业已存在");
                                    LambdaQueryWrapper<ClassTable> classWrapper = new LambdaQueryWrapper<>();
                                    classWrapper
                                            .eq(ClassTable::getMajorUuid,majorTable.getMajorUuid())
                                            .eq(ClassTable::getClassName,item.getClassName());
                                    ClassTable classTable = classTableMapper.selectOne(classWrapper);
                                    if(item.getClassName() != null && classTableMapper.exists(classWrapper)) {
                                        return Rest.fail(msg, "导入失败，院系、专业、课程已存在");
                                    }else{
                                        System.out.println("课程不存在");
                                        if(item.getClassName() != null){
                                            System.out.println("课程名称不为空");
                                            ClassTable classTable1 = new ClassTable();
                                            classTable1.setClassName(item.getClassName());
                                            classTable1.setClassIntro(item.getClassIntro());
                                            classTable1.setMajorUuid(majorTable.getMajorUuid());
                                            classTableMapper.insert(classTable1);
                                        }
                                    }
                                }else{
                                    System.out.println("专业不存在");
                                    if(item.getMajorName() != null){
                                        System.out.println("专业名称不为空");
                                        MajorTable majorTable1 = new MajorTable();
                                        majorTable1.setMajorName(item.getMajorName());
                                        majorTable1.setMajorIntro(item.getMajorIntro());
                                        majorTable1.setDepartUuid(departTable.getDepartUuid());
                                        majorTableMapper.insert(majorTable1);
                                    }
                                    //存入课程
                                    LambdaQueryWrapper<MajorTable> majorWrapper1 = new LambdaQueryWrapper<>();
                                    majorWrapper1
                                            .eq(MajorTable::getDepartUuid,departTable.getDepartUuid())
                                            .eq(MajorTable::getMajorName,item.getMajorName());
                                    MajorTable majorTable2 = majorTableMapper.selectOne(majorWrapper1);
                                    if(item.getClassName() != null) {
                                        ClassTable classTable1 = new ClassTable();
                                        classTable1.setClassName(item.getClassName());
                                        classTable1.setClassIntro(item.getClassIntro());
                                        classTable1.setMajorUuid(majorTable2.getMajorUuid());
                                        classTableMapper.insert(classTable1);
                                    }
                                }
                            }else{
                                System.out.println("院系不存在");
                                if(item.getDepartName() != null){
                                    System.out.println("院系名称不为空");
                                    //存入院系
                                    DepartTable departTable1 = new DepartTable();
                                    departTable1.setDepartName(item.getDepartName());
                                    departTable1.setDepartIntro(item.getDepartIntro());
                                    departTableMapper.insert(departTable1);
                                }
                                LambdaQueryWrapper<DepartTable> departWrapper1 = new LambdaQueryWrapper<>();
                                departWrapper1.eq(DepartTable::getDepartName,item.getDepartName());
                                DepartTable departTable2 = departTableMapper.selectOne(departWrapper1);
                                //存入专业
                                if(item.getMajorName() != null){
                                    MajorTable majorTable1 = new MajorTable();
                                    majorTable1.setMajorName(item.getMajorName());
                                    majorTable1.setMajorIntro(item.getMajorIntro());
                                    majorTable1.setDepartUuid(departTable2.getDepartUuid());
                                    majorTableMapper.insert(majorTable1);
                                }
                                //存入课程
                                LambdaQueryWrapper<MajorTable> majorWrapper1 = new LambdaQueryWrapper<>();
                                majorWrapper1.eq(MajorTable::getMajorName,item.getMajorName());
                                if(item.getClassName() != null){
                                    MajorTable majorTable2 = majorTableMapper.selectOne(majorWrapper1);
                                    ClassTable classTable1 = new ClassTable();
                                    classTable1.setClassName(item.getClassName());
                                    classTable1.setClassIntro(item.getClassIntro());
                                    classTable1.setMajorUuid(majorTable2.getMajorUuid());
                                    classTableMapper.insert(classTable1);
                                }
                            }
                        }
                        break;
                    case "UserTable":
                        List<UserAll> userData = new ArrayList<>();
                        userData = EasyExcel.read(file.getInputStream())
                                .head(UserAll.class)
                                .sheet()
                                .headRowNumber(1)
                                .doReadSync();
                        for(UserAll item : userData){
                            //检查用户信息必填项是否为空
                            if(item.getUserName() == null || item.getUserPassword() == null || item.getUserRole() == null || item.getUserAge() == null || item.getUserGender() == null || item.getUserPhone() == null || item.getUserEmail() == null){
                                failList.add(item.getUserName());
                                System.out.println("用户信息不完整");
                                continue;
                            }
                            //存储身份信息状态
                            boolean flag = true;
                            switch (item.getUserRole()){
                                case "student":
                                    //检查学生信息必填项是否为空
                                    if(item.getMajorName() == null || item.getEntryDate() == null || item.getGraduationDate() == null){
                                        failList.add(item.getUserName());
                                        flag = false;
                                    }else{
                                        //生成学生id
                                        item.setStudentId(String.valueOf(new Date().getTime()));
                                    }
                                    break;
                                case "candidate":
                                    //检查考生信息必填项是否为空
                                    if(item.getMajorName() == null || item.getCandidateStatus() == null){
                                        failList.add(item.getUserName());
                                        flag = false;
                                    }else{
                                        //生成考生id
                                        item.setCandidateId(String.valueOf(new Date().getTime()));
                                    }
                                    break;
                                case "teacher":
                                    //检查教师信息必填项是否为空
                                    if(item.getTeacherIntro() == null || item.getDepartName() == null || item.getClassName() == null){
                                        failList.add(item.getUserName());
                                        flag = false;
                                    }else{
                                        //生成教师id
                                        item.setTeacherId(String.valueOf(new Date().getTime()));
                                    }
                                    break;
                            }
                            //如果身份信息不完整则不录入
                            if(!flag){
                                System.out.println("用户信息不完整");
                                continue;
                            }
                            //通过导入的院系、专业、课程名字获取对应的uuid
                            if(item.getClassName() != null){
                                LambdaQueryWrapper<ClassTable> classWrapper = new LambdaQueryWrapper<>();
                                classWrapper.eq(ClassTable::getClassName,item.getClassName()).last("limit 1");
                                item.setClassUuid(classTableMapper.selectOne(classWrapper).getClassUuid());
                            }
                            if(item.getDepartName() != null){
                                LambdaQueryWrapper<DepartTable> departWrapper = new LambdaQueryWrapper<>();
                                departWrapper.eq(DepartTable::getDepartName,item.getDepartName());
                                item.setDepartUuid(departTableMapper.selectOne(departWrapper).getDepartUuid());
                            }
                            if(item.getMajorName() != null){
                                LambdaQueryWrapper<MajorTable> majorWrapper1 = new LambdaQueryWrapper<>();
                                majorWrapper1.eq(MajorTable::getMajorName,item.getMajorName()).last("limit 1");
                                item.setMajorUuid(majorTableMapper.selectOne(majorWrapper1).getMajorUuid());
                            }
                            //密码加密
                            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                            String userPassword = bCryptPasswordEncoder.encode(item.getUserPassword());
                            LambdaQueryWrapper<UserTable> userWrapper = new LambdaQueryWrapper<>();
                            userWrapper
                                    .eq(UserTable::getUserName,item.getUserName());
                            //判断用户是否存在 如果存在则更新用户信息 如果不存在则插入用户信息
                            if(userTableMapper.exists(userWrapper)){
                                List<UserTable> userTableList = userTableMapper.selectList(userWrapper);
                                userTableList.stream().forEach(userTable -> {
                                    if(bCryptPasswordEncoder.matches(item.getUserPassword(),userTable.getUserPassword())){
                                        existList.add(item.getUserName());
                                        item.setUserUuid(userTable.getUserUuid());
                                        userTableService.updateUserInfo(item);
                                        System.out.println("用户已存在");
                                    }
                                });
                            }else{
                                    UserTable userTable = new UserTable();
                                    userTable.setUserName(item.getUserName());
                                    userTable.setUserPassword(userPassword);
                                    userTable.setUserRole(item.getUserRole());
                                    userTable.setUserAge(item.getUserAge());
                                    userTable.setUserGender(item.getUserGender());
                                    userTable.setUserEmail(item.getUserEmail());
                                    userTable.setUserPhone(item.getUserPhone());
                                    userTableMapper.insert(userTable);
                                    String userUuid = userTableMapper.selectOne(userWrapper).getUserUuid();
                                    //根据用户身份信息存入对应的表
                                    switch (userTable.getUserRole()){
                                        case "candidate":
                                            CandidateTable candidateTable = new CandidateTable();
                                            candidateTable.setUserUuid(userUuid);
                                            switch (item.getCandidateStatus()){
                                                case "初试":
                                                    candidateTable.setCandidateStatus("0");
                                                    break;
                                                case "复试":
                                                    candidateTable.setCandidateStatus("1");
                                                    break;
                                                case "调剂":
                                                    candidateTable.setCandidateStatus("2");
                                                    break;
                                                case "录取":
                                                    candidateTable.setCandidateStatus("3");
                                                    break;
                                                case "未录取":
                                                    candidateTable.setCandidateStatus("4");
                                                    break;
                                            }
                                            candidateTable.setMajorUuid(item.getMajorUuid());
                                            candidateTable.setCandidateId(item.getCandidateId());
                                            candidateTable.setExamPlace(item.getExamPlace());
                                            candidateTableMapper.insert(candidateTable);
                                            break;
                                        case "student":
                                            StudentTable studentTable = new StudentTable();
                                            studentTable.setUserUuid(userUuid);
                                            studentTable.setStudentId(item.getStudentId());
                                            studentTable.setEntryDate(item.getEntryDate());
                                            studentTable.setGraduationDate(item.getGraduationDate());
                                            studentTable.setMajorUuid(item.getMajorUuid());
                                            studentTableMapper.insert(studentTable);
                                            break;
                                        case "teacher":
                                            TeacherTable teacherTable = new TeacherTable();
                                            teacherTable.setUserUuid(userUuid);
                                            teacherTable.setTeacherId(item.getTeacherId());
                                            teacherTable.setTeacherIntro(item.getTeacherIntro());
                                            teacherTable.setClassUuid(item.getClassUuid());
                                            teacherTable.setDepartUuid(item.getDepartUuid());
                                            teacherTableMapper.insert(teacherTable);
                                            break;
                                    }
                                }
                            }
                        break;
                    case "ExamScoreTable":
                        List<UserScoreAll> userScoreData = EasyExcel.read(file.getInputStream()).head(UserScoreAll.class).sheet().doReadSync();
                        for(UserScoreAll item : userScoreData){
                            //判断当前信息是否必填
                            if(item.getUserName() == null || item.getExamType() == null || item.getExamScore() == null || item.getExamClassName() == null || item.getCandidateId() == null){
                                failList.add(item.getUserName());
                                System.out.println("考生信息不完整");
                                continue;
                            }
                            //判断当前考生是否存在
                            LambdaQueryWrapper<UserTable> userWrapper = new LambdaQueryWrapper<>();
                            userWrapper.eq(UserTable::getUserName,item.getUserName());
                            List<UserTable> userTableList = userTableMapper.selectList(userWrapper);
                            //通过用户姓名和考生id确认到唯一考生是否存在
                            LambdaQueryWrapper<CandidateTable> candidateWrapper = new LambdaQueryWrapper<>();
                            AtomicInteger uniqueCount = new AtomicInteger();
                            userTableList.stream().forEach(userTable -> {
                                candidateWrapper
                                        .eq(CandidateTable::getCandidateId,item.getCandidateId())
                                        .eq(CandidateTable::getUserUuid,userTable.getUserUuid());
                                if(candidateTableMapper.exists(candidateWrapper)){
                                    uniqueCount.getAndAdd(1);
                                    //如果唯一考生存在则更新考生信息
                                    if(uniqueCount.get() == 1){
                                        item.setUserUuid(userTable.getUserUuid());
                                    }
                                }else{
                                    failList.add(item.getUserName());
                                    System.out.println("考生不存在");
                                }
                            });
                            //如果唯一考生不存在则跳过
                            if(uniqueCount.get() != 1){
                                continue;
                            }
                            ExamScoreTable examScoreTable = new ExamScoreTable();
                            //转换考试类型
                            boolean examTypeFlag = true;
                            switch (item.getExamType()){
                                case "初试":
                                    examScoreTable.setExamType("0");
                                    break;
                                case "复试":
                                    examScoreTable.setExamType("1");
                                    break;
                                case "调剂":
                                    examScoreTable.setExamType("2");
                                    break;
                                default:
                                    failList.add(item.getUserName());
                                    System.out.println("考试类型错误");
                                    examTypeFlag = false;
                            }
                            if(!examTypeFlag){
                                continue;
                            }
                            //转换考试科目
                            boolean examSubjectFlag = true;
                            //先获取到所属专业的uuid
                            LambdaQueryWrapper<MajorTable> majorWrapper = new LambdaQueryWrapper<>();
                            majorWrapper.eq(MajorTable::getMajorName,item.getMajorName());
                            List<MajorTable> majorTableList = majorTableMapper.selectList(majorWrapper);
                            //如果重复的专业里有这个科目则获取科目uuid
                            majorTableList.stream().forEach(majorTable -> {
                                //获取科目uuid
                                LambdaQueryWrapper<ExamClassTable> examClassTableLambdaQueryWrapper = new LambdaQueryWrapper<>();
                                examClassTableLambdaQueryWrapper
                                        .eq(ExamClassTable::getExamClassName,item.getExamClassName())
                                        .eq(ExamClassTable::getMajorUuid, majorTable.getMajorUuid());
                                if(examClassTableMapper.exists(examClassTableLambdaQueryWrapper)){
                                    ExamClassTable examClassTable = examClassTableMapper.selectOne(examClassTableLambdaQueryWrapper);
                                    item.setExamClassUuid(examClassTable.getExamClassUuid());
                                }

                            });
                            //检查该类型、科目考试是否已存在
                            LambdaQueryWrapper<ExamScoreTable> examScoreWrapper = new LambdaQueryWrapper<>();
                            examScoreWrapper
                                    .eq(ExamScoreTable::getUserUuid,item.getUserUuid())
                                    .eq(ExamScoreTable::getExamType,examScoreTable.getExamType())
                                    .eq(ExamScoreTable::getExamClassUuid,item.getExamClassUuid());
                            if(examScoreTableMapper.exists(examScoreWrapper)){
                                failList.add(item.getUserName());
                                System.out.println("该考试成绩已存在");
                                continue;
                            }
                            examScoreTable.setUserUuid(item.getUserUuid());
                            examScoreTable.setExamClassUuid(item.getExamClassUuid());
                            examScoreTable.setExamScore(item.getExamScore());
                            examScoreTableMapper.insert(examScoreTable);
                        }
                }
                result.put("successList",successList);
                result.put("successCount",successList.size());
                result.put("failList",failList);
                result.put("failCount",failList.size());
                result.put("existList",existList);
                result.put("existCount",existList.size());
                result.put("message","导入成功");
                return Rest.success(msg,result);
            }
            return Rest.fail(msg,"导入失败");
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //单个文件上传
//    @PostMapping("upload")
    @PostMapping("upload")
    public Rest upload(@RequestBody MultipartFile file){
        String msg = "上传文件";
        try{
            return saveFile(file,msg);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //多文件上传
    @PostMapping("multiUpload")
    public Rest multiUpload(@RequestBody MultipartFile[] files){
        String msg = "上传文件";
        List<Rest> result = new ArrayList<Rest>();
        for(MultipartFile file : files){
            result.add(saveFile(file,msg));
        }
        return Rest.success(msg,result);
    }

    //富文本上传
    @PostMapping("editorUpload")
    public Map<String,Object> editorUpload(@RequestBody MultipartFile file){
        try{
            return saveTextFile(file);
        }catch (Exception e){
            Map<String,Object> result = new HashMap<String,Object>();
            result.put("errno",1);
            result.put("message","上传失败");
            return result;
        }
    }

    //富文本视频上传
    @PostMapping("editorVideoUpload")
    public Map<String,Object> editorVideoUpload(@RequestBody MultipartFile file){
        try{
            return saveVideoFile(file);
        }catch (Exception e){
            Map<String,Object> result = new HashMap<String,Object>();
            result.put("errno",1);
            result.put("message","上传失败");
            return result;
        }
    }



    private Rest saveFile(MultipartFile file,String msg){
        if(file.isEmpty()){
            return Rest.fail(msg,"未检测到文件");
        }
        String fileName = file.getOriginalFilename();   //获取上传文件原名称
        String filePath = "D:/code/java/public/";

        File temp = new File(filePath);
        if(!temp.exists()){
            temp.mkdirs();
        }

        File localFile = new File(filePath + fileName);
        try{
            file.transferTo(localFile); //把上传的文件保存到本地
            return Rest.success(msg,"http://localhost:8080/anixuil/public/" + fileName);
        }catch (IOException e){
            e.printStackTrace();
            return Rest.fail(msg,e);
        }
    }

    //富文本上传
    private Map<String,Object> saveTextFile(MultipartFile file) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (file.isEmpty()) {
            result.put("errno", 1);
            result.put("message", "未检测到文件");
            return result;
        }
        String filePath = "D:/code/java/public/";
        String fileName = file.getOriginalFilename();   //获取上传文件原名称
        File temp = new File(filePath);
        if (!temp.exists()) {
            temp.mkdirs();
        }

        File localFile = new File(filePath + fileName);
        try {
            file.transferTo(localFile); //把上传的文件保存到本地
            result.put("errno", 0);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("url", "http://localhost:8080/anixuil/public/" + fileName);
            data.put("alt", fileName);
            data.put("href", "http://localhost:8080/anixuil/public/" + fileName);
            result.put("data", data);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result.put("errno", 1);
            result.put("message", e);
            return result;
        }
    }

    //富文本视频上传
    private Map<String,Object> saveVideoFile(MultipartFile file){
        Map<String, Object> result = new HashMap<String, Object>();
        if (file.isEmpty()) {
            result.put("errno", 1);
            result.put("message", "未检测到文件");
            return result;
        }
        String filePath = "D:/code/java/public/";
        String fileName = file.getOriginalFilename();   //获取上传文件原名称
        File temp = new File(filePath);
        if (!temp.exists()) {
            temp.mkdirs();
        }

        File localFile = new File(filePath + fileName);
        try {
            file.transferTo(localFile); //把上传的文件保存到本地
            result.put("errno", 0);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("url", "http://localhost:8080/anixuil/public/" + fileName);
            data.put("poster", "http://localhost:8080/anixuil/public/" + fileName);
            result.put("data", data);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result.put("errno", 1);
            result.put("message", e);
            return result;
        }
    }

}
