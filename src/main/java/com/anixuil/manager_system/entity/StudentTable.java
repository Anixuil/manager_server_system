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
@TableName("student_table")
@ApiModel(value = "StudentTable对象", description = "")
public class StudentTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "student_uuid", type = IdType.ASSIGN_UUID)
    @ExcelProperty("学生UUID")
    private String studentUuid;

    @TableField("user_uuid")
    @ExcelProperty("用户UUID")
    private String userUuid;

    @TableField("student_id")
    @ExcelProperty("学生ID")
    private String studentId;

    @TableField("major_uuid")
    @ExcelProperty("专业UUID")
    private String majorUuid;

    @TableField("entry_date")
    @ExcelProperty("入学时间")
    private String entryDate;

    @TableField("graduation_date")
    @ExcelProperty("毕业时间")
    private String graduationDate;

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
