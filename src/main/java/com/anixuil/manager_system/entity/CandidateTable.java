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
@TableName("candidate_table")
@ApiModel(value = "CandidateTable对象", description = "")
public class CandidateTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("candidate_uuid")
    private String candidateUuid;

    @TableField("user_uuid")
    private String userUuid;

    @TableField("major_uuid")
    private String majorUuid;

    @TableField("candidate_id")
    private String candidateId;

    @TableField("candidate_status")
    private String candidateStatus;

    @TableField("create_date")
    private String createDate;
}
