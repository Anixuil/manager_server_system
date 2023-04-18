package com.anixuil.manager_system.service;

import com.anixuil.manager_system.entity.MajorTable;
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
public interface MajorTableService extends IService<MajorTable> {
    //新增专业
    Rest addMajor(MajorTable majorTable);

    //修改专业
    Rest updateMajor(MajorTable majorTable);

    //删除专业
    Rest deleteMajor(MajorTable majorTable);

    //获取专业列表
    Rest getMajorList(Integer pageNum, Integer pageSize);
}
