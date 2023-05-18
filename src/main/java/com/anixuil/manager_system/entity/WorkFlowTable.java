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
@TableName("work_flow_table")
@ApiModel(value = "WorkFlowTable对象", description = "")
public class WorkFlowTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="work_flow_uuid",type= IdType.ASSIGN_UUID)
    private String workFlowUuid;

    @TableField("work_flow_title")
    private String workFlowTitle;

    @TableField("work_flow_desc")
    private String workFlowDesc;

    @TableField("work_flow_date")
    private String workFlowDate;

    @TableField("work_flow_index")
    private Integer workFlowIndex;

    @TableField("work_flow_status")
    private Boolean workFlowStatus;

    @TableField("work_flow_type")
    private String workFlowType;

    @TableField("work_flow_path")
    private String workFlowPath;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    @ExcelProperty("创建时间")
    private Timestamp createDate;

    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    @ExcelProperty("更新时间")
    private Timestamp updateDate;

    @TableField("is_delete")
    private String isDelete;
}
