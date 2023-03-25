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
@TableName("student_table")
@ApiModel(value = "StudentTable对象", description = "")
public class StudentTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("student_uuid")
    private String studentUuid;

    @TableField("user_uuid")
    private String userUuid;

    @TableField("student_id")
    private String studentId;

    @TableField("major_uuid")
    private String majorUuid;

    @TableField("entry_date")
    private String entryDate;

    @TableField("graduation_date")
    private String graduationDate;

    @TableField("create_date")
    private String createDate;
}
