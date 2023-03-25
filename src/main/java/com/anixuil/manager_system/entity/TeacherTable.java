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
@TableName("teacher_table")
@ApiModel(value = "TeacherTable对象", description = "")
public class TeacherTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("teacher_uuid")
    private String teacherUuid;

    @TableField("user_uuid")
    private String userUuid;

    @TableField("depart_uuid")
    private String departUuid;

    @TableField("teacher_id")
    private String teacherId;

    @TableField("teacher_intro")
    private String teacherIntro;

    @TableField("class_uuid")
    private String classUuid;

    @TableField("create_date")
    private String createDate;
}
