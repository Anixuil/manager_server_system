package com.anixuil.manager_system.entity;

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
 * @since 2023-03-09
 */
@Getter
@Setter
@TableName("index_wheel_table")
@ApiModel(value = "IndexWheelTable对象", description = "")
public class IndexWheelTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "index_wheel_uuid", type = IdType.ASSIGN_UUID)
    private String indexWheelUuid;

    @TableField("index_wheel_title")
    private String indexWheelTitle;

    @TableField("index_wheel_desc")
    private String indexWheelDesc;

    @TableField("index_wheel_image")
    private String indexWheelImage;

    @TableField("index_wheel_href")
    private String indexWheelHref;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Timestamp createDate;

    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    private Timestamp updateDate;

    @TableField("is_delete")
    private String isDelete;
}
