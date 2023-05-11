package com.anixuil.manager_system.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartAll {
    @ExcelProperty("院系名称")
    private String departName;

    @ExcelProperty("院系简介")
    private String departIntro;

    @ExcelProperty("院系UUID")
    private String departUuid;

    @ExcelProperty("专业名称")
    private String majorName;

    @ExcelProperty("专业简介")
    private String majorIntro;

    @ExcelProperty("课程名称")
    private String className;

    @ExcelProperty("课程简介")
    private String classIntro;
}
