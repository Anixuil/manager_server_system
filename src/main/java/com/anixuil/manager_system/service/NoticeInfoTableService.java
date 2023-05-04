package com.anixuil.manager_system.service;

import com.anixuil.manager_system.entity.NoticeInfoTable;
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
public interface NoticeInfoTableService extends IService<NoticeInfoTable> {
    Rest addNoticeInfo(NoticeInfoTable noticeInfoTable);

    Rest updateNoticeInfo(NoticeInfoTable noticeInfoTable);

    Rest deleteNoticeInfo(NoticeInfoTable noticeInfoTable);

    Rest getNoticeInfoList(Integer pageNum,Integer pageSize);
}
