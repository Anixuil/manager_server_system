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
@TableName("exam_class_table")
@ApiModel(value = "ExamClassTable对象", description = "")
public class ExamClassTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "exam_class_uuid", type = IdType.ASSIGN_UUID)
    @ExcelProperty("考试科目UUID")
    private String examClassUuid;

    @TableField("major_uuid")
    @ExcelProperty("专业UUID")
    private String majorUuid;

    @TableField("exam_class_name")
    @ExcelProperty("考试科目名称")
    private String examClassName;

    @TableField("exam_class_desc")
    @ExcelProperty("考试科目描述")
    private String examClassDesc;

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
