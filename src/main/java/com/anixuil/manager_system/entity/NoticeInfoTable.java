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
@TableName("notice_info_table")
@ApiModel(value = "NoticeInfoTable对象", description = "")
public class NoticeInfoTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "notice_info_uuid", type = IdType.ASSIGN_UUID)
    private String noticeInfoUuid;

    @TableField("notice_info_title")
    private String noticeInfoTitle;

    @TableField("notice_info_intro")
    private String noticeInfoIntro;

    @TableField("notice_info_content")
    private String noticeInfoContent;

    @TableField("notice_info_attachment")
    private String noticeInfoAttachment;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Timestamp createDate;

    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    private Timestamp updateDate;

    @TableField("is_delete")
    private String isDelete;
}
