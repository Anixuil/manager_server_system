package com.anixuil.manager_system.service;

import com.anixuil.manager_system.entity.DepartTable;
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
public interface DepartTableService extends IService<DepartTable> {
    //获取所有院系
    public Rest getAllDepart(Integer pageNum, Integer pageSize);

    //添加院系
    public Rest addDepart(DepartTable departTable);

    //修改院系
    public Rest updateDepart(DepartTable departTable);

    //删除院系
    public Rest deleteDepart(DepartTable departTable);


}
