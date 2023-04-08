package com.anixuil.manager_system.entity;

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
@TableName("log_table")
@ApiModel(value = "LogTable对象", description = "")
public class LogTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "log_uuid", type = IdType.ASSIGN_UUID)
    private String logUuid;

    @TableField("log_title")
    private String logTitle;

    @TableField("user_uuid")
    private String userUuid;

    @TableField("log_content")
    private String logContent;

    @TableField("log_status")
    private String logStatus;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Timestamp createDate;
}
