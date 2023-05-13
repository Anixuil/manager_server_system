package com.anixuil.manager_system.service;

import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.WorkFlowTable;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anixuil
 * @since 2023-03-09
 */
public interface WorkFlowTableService extends IService<WorkFlowTable> {
    Rest addWorkFlow(WorkFlowTable workFlowTable);

    Rest updateWorkFlow(WorkFlowTable workFlowTable);

    Rest deleteWorkFlow(WorkFlowTable workFlowTable);

    Rest getWorkFlowList(Integer pageNum, Integer pageSize,String workFlowUuid,String workFlowTitle, String workFlowDesc, String workFlowType);
}
