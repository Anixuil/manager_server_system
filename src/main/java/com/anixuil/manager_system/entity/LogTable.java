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
@TableName("log_table")
@ApiModel(value = "LogTable对象", description = "")
public class LogTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("log_uuid")
    private String logUuid;

    @TableField("log_title")
    private String logTitle;

    @TableField("user_uuid")
    private String userUuid;

    @TableField("log_content")
    private String logContent;

    @TableField("log_status")
    private String logStatus;

    @TableField("create_date")
    private String createDate;
}
