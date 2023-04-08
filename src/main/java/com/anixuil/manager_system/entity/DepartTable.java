package com.anixuil.manager_system.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

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
@TableName("depart_table")
@ApiModel(value = "DepartTable对象", description = "")
public class DepartTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("depart_name")
    @ExcelProperty("院系名称")
    private String departName;

    @TableField("depart_intro")
    @ExcelProperty("院系简介")
    private String departIntro;

    @TableId(value = "depart_uuid", type = IdType.ASSIGN_UUID)
    @ExcelProperty("院系UUID")
    private String departUuid;

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
