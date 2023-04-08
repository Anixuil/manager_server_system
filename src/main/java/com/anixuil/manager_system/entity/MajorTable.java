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
@TableName("major_table")
@ApiModel(value = "MajorTable对象", description = "")
public class MajorTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "major_uuid", type = IdType.ASSIGN_UUID)
    @ExcelProperty("专业UUID")
    private String majorUuid;

    @TableField("depart_uuid")
    @ExcelProperty("院系UUID")
    private String departUuid;

    @TableField("major_name")
    @ExcelProperty("专业名称")
    private String majorName;

    @TableField("major_intro")
    @ExcelProperty("专业简介")
    private String majorIntro;

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
