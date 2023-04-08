package com.anixuil.manager_system.config;

import com.anixuil.manager_system.utils.Datetime;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class MpMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createDate", Datetime.getTimestamp(),metaObject);
        this.setFieldValByName("updateDate",Datetime.getTimestamp(),metaObject);
        this.setFieldValByName("user_password","swxy123456",metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateDate",Datetime.getTimestamp(),metaObject);
    }
}
