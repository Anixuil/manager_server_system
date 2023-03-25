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
@TableName("exam_class_table")
@ApiModel(value = "ExamClassTable对象", description = "")
public class ExamClassTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("exam_class_uuid")
    private String examClassUuid;

    @TableField("major_uuid")
    private String majorUuid;

    @TableField("exam_class_name")
    private String examClassName;

    @TableField("exam_class_desc")
    private String examClassDesc;

    @TableField("create_date")
    private String createDate;
}
