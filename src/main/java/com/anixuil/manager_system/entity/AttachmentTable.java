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
 * @since 2023-05-07
 */
@Getter
@Setter
@TableName("attachment_table")
@ApiModel(value = "AttachmentTable对象", description = "")
public class AttachmentTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("notice_info_uuid")
    private String noticeInfoUuid;

    @TableId(value="attachment_uuid",type = IdType.ASSIGN_UUID)
    private String attachmentUuid;

    @TableField("attachment_name")
    private String attachmentName;

    @TableField("attachment_href")
    private String attachmentHref;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    @ExcelProperty("创建时间")
    private Timestamp createDate;

    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    @ExcelProperty("更新时间")
    private Timestamp updateDate;

    @TableField("is_delete")
    private String isDelete;
}
