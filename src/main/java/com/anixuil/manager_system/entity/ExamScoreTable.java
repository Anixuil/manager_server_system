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
@TableName("exam_score_table")
@ApiModel(value = "ExamScoreTable对象", description = "")
public class ExamScoreTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("exam_score_uuid")
    private String examScoreUuid;

    @TableField("user_uuid")
    private String userUuid;

    @TableField("exam_class_uuid")
    private String examClassUuid;

    @TableField("exam_score")
    private Double examScore;

    @TableField("create_date")
    private String createDate;
}
