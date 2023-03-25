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
@TableName("user_table")
@ApiModel(value = "UserTable对象", description = "")
public class UserTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("user_uuid")
    private String userUuid;

    @TableField("user_name")
    private String userName;

    @TableField("user_password")
    private String userPassword;

    @TableField("user_email")
    private String userEmail;

    @TableField("user_phone")
    private String userPhone;

    @TableField("user_role")
    private String userRole;

    @TableField("user_gender")
    private String userGender;

    @TableField("user_age")
    private Integer userAge;

    @TableField("create_date")
    private Timestamp createDate;
}
