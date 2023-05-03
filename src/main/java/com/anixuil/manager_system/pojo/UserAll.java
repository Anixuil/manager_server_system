package com.anixuil.manager_system.pojo;

import java.sql.Timestamp;

public class UserAll {
    private String userUuid;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userRole;
    private String userGender;
    private Integer userAge;
    private String candidateUuid;
    private String majorUuid;
    private String candidateId;
    private String candidateStatus;
    private String examPlace;
    private String teacherUuid;
    private String userPassword;

    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public UserAll() {
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getCandidateUuid() {
        return candidateUuid;
    }

    public void setCandidateUuid(String candidateUuid) {
        this.candidateUuid = candidateUuid;
    }

    public String getMajorUuid() {
        return majorUuid;
    }

    public void setMajorUuid(String majorUuid) {
        this.majorUuid = majorUuid;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateStatus() {
        return candidateStatus;
    }

    public void setCandidateStatus(String candidateStatus) {
        this.candidateStatus = candidateStatus;
    }

    public String getExamPlace() {
        return examPlace;
    }

    public void setExamPlace(String examPlace) {
        this.examPlace = examPlace;
    }

    public String getTeacherUuid() {
        return teacherUuid;
    }

    public void setTeacherUuid(String teacherUuid) {
        this.teacherUuid = teacherUuid;
    }

    public String getDepartUuid() {
        return departUuid;
    }

    public void setDepartUuid(String departUuid) {
        this.departUuid = departUuid;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherIntro() {
        return teacherIntro;
    }

    public void setTeacherIntro(String teacherIntro) {
        this.teacherIntro = teacherIntro;
    }

    public String getClassUuid() {
        return classUuid;
    }

    public void setClassUuid(String classUuid) {
        this.classUuid = classUuid;
    }

    public String getStudentUuid() {
        return studentUuid;
    }

    public void setStudentUuid(String studentUuid) {
        this.studentUuid = studentUuid;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getGraduationDate() {
        return graduationDate;
    }

    public void setGradutaionDate(String graduationDate) {
        this.graduationDate = graduationDate;
    }

    private String departUuid;
    private String teacherId;
    private String teacherIntro;
    private String classUuid;
    private String studentUuid;
    private String studentId;
    private String entryDate;
    private String graduationDate;
    private Timestamp createDate;
    private Timestamp updateDate;
    private Double firstScore;
    private Double secondScore;
    private Double thirdScore;

    public Double getFirstScore() {
        return firstScore;
    }

    public void setFirstScore(Double firstScore) {
        this.firstScore = firstScore;
    }

    public Double getSecondScore() {
        return secondScore;
    }

    public void setSecondScore(Double secondScore) {
        this.secondScore = secondScore;
    }

    public Double getThirdScore() {
        return thirdScore;
    }

    public void setThirdScore(Double thirdScore) {
        this.thirdScore = thirdScore;
    }

    public double getExamScore() {
        return examScore;
    }

    public void setExamScore(double examScore) {
        this.examScore = examScore;
    }

    private double examScore;

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
}
