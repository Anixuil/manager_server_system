package com.anixuil.manager_system.entity;

import com.alibaba.excel.annotation.ExcelProperty;
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
 * @since 2023-05-20
 */
@Getter
@Setter
@TableName("stu_teach_relation_table")
@ApiModel(value = "StuTeachRelationTable对象", description = "")
public class StuTeachRelationTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "relation_uuid",type = IdType.ASSIGN_UUID)
    private String relationUuid;

    @TableField("stu_user_uuid")
    private String stuUserUuid;

    @TableField("teach_user_uuid")
    private String teachUserUuid;

    @TableField("relation_type")
    private String relationType;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Timestamp createDate;

    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    private Timestamp updateDate;

    @TableField("is_delete")
    private String isDelete;
}
