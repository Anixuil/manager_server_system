package com.anixuil.manager_system.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserScoreAll {
    private String userUuid;
    @ExcelProperty("考生号")
    private String candidateId;
    @ExcelProperty("姓名")
    private String userName;
    private String examScoreUuid;
    private String examClassUuid;
    @ExcelProperty("科目")
    private String examClassName;
    @ExcelProperty("分数")
    private Double examScore;
    @ExcelProperty("类别")
    private String examType;
    @ExcelProperty("专业")
    private String majorName;
    private String majorUuid;
}
