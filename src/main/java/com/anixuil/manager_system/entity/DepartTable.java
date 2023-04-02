package com.anixuil.manager_system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

    @TableId("depart_uuid")
    private String departUuid;

    @TableField("depart_name")
    private String departName;

    @TableField("depart_intro")
    private String departIntro;

    @TableField("create_date")
    private Timestamp createDate;

}
