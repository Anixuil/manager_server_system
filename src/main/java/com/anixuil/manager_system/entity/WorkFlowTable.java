package com.anixuil.manager_system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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

    @TableId("work_flow_uuid")
    private String workFlowUuid;

    @TableField("work_flow_title")
    private String workFlowTitle;

    @TableField("work_flow_desc")
    private String workFlowDesc;

    @TableField("work_flow_image")
    private String workFlowImage;

    @TableField("work_flow_index")
    private Integer workFlowIndex;

    @TableField("create_date")
    private String createDate;
}
