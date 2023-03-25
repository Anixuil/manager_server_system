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
@TableName("enro_plan_table")
@ApiModel(value = "EnroPlanTable对象", description = "")
public class EnroPlanTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("enro_plan_uuid")
    private String enroPlanUuid;

    @TableField("major_uuid")
    private String majorUuid;

    @TableField("enro_plan_number")
    private Integer enroPlanNumber;

    @TableField("enro_real_number")
    private Integer enroRealNumber;

    @TableField("create_date")
    private String createDate;
}
