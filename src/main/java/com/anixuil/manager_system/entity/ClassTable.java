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
@TableName("class_table")
@ApiModel(value = "ClassTable对象", description = "")
public class ClassTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "class_uuid", type = IdType.ASSIGN_UUID)
    @ExcelProperty("课程UUID")
    private String classUuid;

    @TableField("class_name")
    @ExcelProperty("课程名称")
    private String className;

    @TableField("class_intro")
    @ExcelProperty("课程简介")
    private String classIntro;

    @TableField("major_uuid")
    @ExcelProperty("专业UUID")
    private String majorUuid;

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
