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
@TableName("exam_score_table")
@ApiModel(value = "ExamScoreTable对象", description = "")
public class ExamScoreTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "exam_score_uuid", type = IdType.ASSIGN_UUID)
    @ExcelProperty("考试成绩UUID")
    private String examScoreUuid;

    @TableField("user_uuid")
    @ExcelProperty("用户UUID")
    private String userUuid;

    @TableField("exam_class_uuid")
    @ExcelProperty("考试科目UUID")
    private String examClassUuid;

    @TableField("exam_score")
    @ExcelProperty("考试成绩")
    private Double examScore;

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
