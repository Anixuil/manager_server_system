package com.anixuil.manager_system.service;

import com.anixuil.manager_system.entity.EnroPlanTable;
import com.anixuil.manager_system.entity.Rest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anixuil
 * @since 2023-03-09
 */
public interface EnroPlanTableService extends IService<EnroPlanTable> {
    //添加招生计划
    Rest addEnroPlan(EnroPlanTable enroPlanTable);

    //修改招生计划
    Rest updateEnroPlan(EnroPlanTable enroPlanTable);

    //删除招生计划
    Rest deleteEnroPlan(EnroPlanTable enroPlanTable);

    //查询招生计划
    Rest getEnroPlanList(Integer pageNum, Integer pageSize);

    Rest getEnroPlanList(Integer pageNum, Integer pageSize, EnroPlanTable params);
}
