package com.anixuil.manager_system.service;

import com.anixuil.manager_system.entity.IndexWheelTable;
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
public interface IndexWheelTableService extends IService<IndexWheelTable> {
    //新增轮播
    public Rest addIndexWheel(IndexWheelTable indexWheelTable);

    //修改轮播
    public Rest updateIndexWheel(IndexWheelTable indexWheelTable);

    //删除轮播
    public Rest deleteIndexWheel(IndexWheelTable indexWheelTable);

    //获取轮播列表
    public Rest getIndexWheelList(Integer pageNum, Integer pageSize);
}
