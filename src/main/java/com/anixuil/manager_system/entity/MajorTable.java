package com.anixuil.manager_system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

    @TableId("major_uuid")
    private String majorUuid;

    @TableField("depart_uuid")
    private String departUuid;

    @TableField("major_name")
    private String majorName;

    @TableField("major_intro")
    private String majorIntro;

    @TableField("create_date")
    private Timestamp createDate;

}
