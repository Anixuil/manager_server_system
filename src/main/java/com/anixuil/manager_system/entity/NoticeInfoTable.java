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
@TableName("notice_info_table")
@ApiModel(value = "NoticeInfoTable对象", description = "")
public class NoticeInfoTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("notice_info_uuid")
    private String noticeInfoUuid;

    @TableField("notice_info_title")
    private String noticeInfoTitle;

    @TableField("notice_info_intro")
    private String noticeInfoIntro;

    @TableField("notice_info_content")
    private String noticeInfoContent;

    @TableField("notice_info_attachment")
    private String noticeInfoAttachment;

    @TableField("create_date")
    private String createDate;
}
