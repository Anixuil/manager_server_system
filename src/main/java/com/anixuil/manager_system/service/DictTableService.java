package com.anixuil.manager_system.service;

import com.anixuil.manager_system.entity.DictTable;
import com.anixuil.manager_system.entity.Rest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anixuil
 * @since 2023-04-09
 */
public interface DictTableService extends IService<DictTable> {

    //新增字典
    Rest addDict(DictTable dictTable);

    //获取字典
    Rest getDict(String dictType,String dictName);
}
