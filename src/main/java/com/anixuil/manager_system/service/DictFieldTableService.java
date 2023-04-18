package com.anixuil.manager_system.service;

import com.anixuil.manager_system.entity.DictFieldTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.pojo.DictData;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anixuil
 * @since 2023-04-09
 */
public interface DictFieldTableService extends IService<DictFieldTable> {
    //获取所有字典字段
    Rest getAllDictField(Integer pageNum, Integer pageSize);

    //新建字典字段
    Rest addDictField(DictData dictData);

    //修改字典字段
    Rest updateDictField(DictFieldTable dictFieldTable);

    //删除字典字段
    Rest deleteDictField(DictFieldTable dictFieldTable);
}
