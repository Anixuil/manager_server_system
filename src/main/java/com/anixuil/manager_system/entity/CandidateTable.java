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
@TableName("candidate_table")
@ApiModel(value = "CandidateTable对象", description = "")
public class CandidateTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "candidate_uuid", type = IdType.ASSIGN_UUID)
    @ExcelProperty("考生UUID")
    private String candidateUuid;

    @TableField("user_uuid")
    @ExcelProperty("用户UUID")
    private String userUuid;

    @TableField("major_uuid")
    @ExcelProperty("专业UUID")
    private String majorUuid;

    @TableField("candidate_id")
    @ExcelProperty("考生ID")
    private String candidateId;

    @TableField("candidate_status")
    @ExcelProperty("考生状态")
    private String candidateStatus;

    @TableField("exam_place")
    @ExcelProperty("考试地点")
    private String examPlace;

    @TableField("exam_date")
    @ExcelProperty("考试日期")
    private String examDate;

    @TableField("information_status")
    @ExcelProperty("信息确认状态")
    private String informationStatus;

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
