package com.anixuil.manager_system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Anixuil
 * @since 2023-04-09
 */
@Getter
@Setter
@TableName("dict_field_table")
@ApiModel(value = "DictFieldTable对象", description = "")
public class DictFieldTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="dict_field_uuid",type = IdType.ASSIGN_UUID)
    private String dictFieldUuid;

    @TableField("dict_name")
    private String dictName;

    @TableField("dict_field_label")
    private String dictFieldLabel;

    @TableField("dict_field_value")
    private String dictFieldValue;
}
