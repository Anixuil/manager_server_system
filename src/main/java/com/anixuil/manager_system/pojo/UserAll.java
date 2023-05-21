package com.anixuil.manager_system.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UserAll {
    private String userUuid;
    @ExcelProperty("姓名")
    private String userName;
    @ExcelProperty("邮箱")
    private String userEmail;
    @ExcelProperty("电话")
    private String userPhone;
    @ExcelProperty("角色")
    private String userRole;
    @ExcelProperty("性别")
    private String userGender;
    @ExcelProperty("年龄")
    private Integer userAge;
    private String candidateUuid;
    @ExcelProperty("本科院校")
    private String undergraduateSchool;
    @ExcelProperty("专业")
    private String majorName;
    private String majorUuid;
    @ExcelProperty("考生ID")
    private String candidateId;
    @ExcelProperty("考生状态")
    private String candidateStatus;
    @ExcelProperty("考试地点")
    private String examPlace;
    @ExcelProperty("考试日期")
    private String examDate;
    private String teacherUuid;
    @ExcelProperty("密码")
    private String userPassword;

    private String userHeadimg;
    private String informationStatus;
    @ExcelProperty("地址")
    private String userAddress;

    private String departUuid;
    @ExcelProperty("院系")
    private String departName;
    @ExcelProperty("教师ID")
    private String teacherId;
    @ExcelProperty("教师简介")
    private String teacherIntro;
    private String classUuid;
    @ExcelProperty("教授课程")
    private String className;
    private String studentUuid;
    @ExcelProperty("学生ID")
    private String studentId;
    @ExcelProperty("入学时间")
    private String entryDate;
    @ExcelProperty("毕业时间")
    private String graduationDate;
    private Timestamp createDate;
    private Timestamp updateDate;

    //relation
    private String relationUuid;
    private String stuUserUuid;
    private String teachUserUuid;
    private String relationType;

}
