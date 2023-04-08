package com.anixuil.manager_system.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Anixuil
 * @since 2023-03-09
 */
@Getter
@Setter
@TableName("teacher_table")
@ApiModel(value = "TeacherTable对象", description = "")
public class TeacherTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "teacher_uuid", type = IdType.ASSIGN_UUID)
    @ExcelProperty("教师UUID")
    private String teacherUuid;

    @TableField("user_uuid")
    @ExcelProperty("用户UUID")
    private String userUuid;

    @TableField("depart_uuid")
    @ExcelProperty("院系UUID")
    private String departUuid;

    @TableField("teacher_id")
    @ExcelProperty("教师ID")
    private String teacherId;

    @TableField("teacher_intro")
    @ExcelProperty("教师简介")
    private String teacherIntro;

    @TableField("class_uuid")
    @ExcelProperty("课程UUID")
    private String classUuid;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    @ExcelProperty("创建时间")
    private Timestamp createDate;

    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    @ExcelProperty("更新时间")
    private Timestamp updateDate;

    @TableField("is_delete")
    @ExcelProperty("是否删除")
    private String isDelete;
}
