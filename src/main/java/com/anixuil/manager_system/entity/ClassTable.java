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
@TableName("class_table")
@ApiModel(value = "ClassTable对象", description = "")
public class ClassTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("class_uuid")
    private String classUuid;

    @TableField("class_name")
    private String className;

    @TableField("class_intro")
    private String classIntro;

    @TableField("major_uuid")
    private String majorUuid;

    @TableField("create_date")
    private String createDate;
}
