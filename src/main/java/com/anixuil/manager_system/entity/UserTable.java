package com.anixuil.manager_system.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;

import io.swagger.annotations.ApiModel;
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

    @TableId(value = "user_uuid", type = IdType.ASSIGN_UUID)
    @ExcelProperty("用户UUID")
    private String userUuid;

    @TableField("user_name")
    @ExcelProperty("用户名")
    private String userName;

    @TableField(value = "user_password",fill = FieldFill.INSERT)
    @ExcelProperty("用户密码")
    private String userPassword;

    @TableField("user_email")
    @ExcelProperty("用户邮箱")
    private String userEmail;

    @TableField("user_phone")
    @ExcelProperty("用户电话")
    private String userPhone;

    @TableField("user_role")
    @ExcelProperty("用户角色")
    private String userRole;

    @TableField("user_gender")
    @ExcelProperty("用户性别")
    private String userGender;

    @TableField("user_age")
    @ExcelProperty("用户年龄")
    private Integer userAge;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    @ExcelProperty("创建时间")
    private Timestamp createDate;

    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    @ExcelProperty("更新时间")
    private Timestamp updateDate;

    @TableField("is_delete")
    @ExcelProperty("是否删除")
    private String isDelete;
}
