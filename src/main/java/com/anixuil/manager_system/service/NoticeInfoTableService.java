package com.anixuil.manager_system.service;

import com.anixuil.manager_system.entity.NoticeInfoTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.pojo.NoticeAll;
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
    Rest addNoticeInfo(NoticeAll noticeAll);

    Rest updateNoticeInfo(NoticeInfoTable noticeInfoTable);

    Rest deleteNoticeInfo(NoticeInfoTable noticeInfoTable);

    Rest getNoticeInfoList(Integer pageNum,Integer pageSize,String noticeInfoTitle,String noticeInfoIntro,String noticeInfoType);

    Rest getOne(String noticeInfoUuid);
}
