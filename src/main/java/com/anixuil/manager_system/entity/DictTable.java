package com.anixuil.manager_system.entity;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
import java.sql.Timestamp;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("dict_table")
@ApiModel(value = "DictTable对象", description = "")
public class DictTable implements Serializable{
    private static final long serialVersionUID = 1L;

    @TableId(value = "dict_uuid", type = IdType.ASSIGN_UUID)
    private String dictUuid;

    @TableField("dict_type")
    private String dictType;

    @TableField("dict_name")
    private String dictName;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Timestamp createDate;

    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    private Timestamp updateDate;

    @TableField("is_delete")
    private String isDelete;
}
