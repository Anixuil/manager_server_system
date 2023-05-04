package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.NoticeInfoTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.mapper.NoticeInfoTableMapper;
import com.anixuil.manager_system.service.NoticeInfoTableService;
import com.anixuil.manager_system.utils.Datetime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anixuil
 * @since 2023-03-09
 */
@Service
public class NoticeInfoTableServiceImpl extends ServiceImpl<NoticeInfoTableMapper, NoticeInfoTable> implements NoticeInfoTableService {

    @Override
    public Rest addNoticeInfo(NoticeInfoTable noticeInfoTable) {
        String msg = "添加公告";
        try {
            boolean result = save(noticeInfoTable);
            if(result){
                return Rest.success(msg,true);
            }
            return Rest.fail(msg,false);
        } catch (Exception e) {
            e.printStackTrace();
            return Rest.fail(msg,e);
        }
    }

    @Override
    public Rest updateNoticeInfo(NoticeInfoTable noticeInfoTable) {
        String msg = "修改公告";
        try {
            boolean result = updateById(noticeInfoTable);
            if(result){
                return Rest.success(msg,true);
            }
            return Rest.fail(msg,false);
        } catch (Exception e) {
            e.printStackTrace();
            return Rest.fail(msg,e);
        }
    }

    @Override
    public Rest deleteNoticeInfo(NoticeInfoTable noticeInfoTable) {
        String msg = "删除公告";
        try {
            boolean result = removeById(noticeInfoTable);
            if(result){
                return Rest.success(msg,true);
            }
            return Rest.fail(msg,false);
        } catch (Exception e) {
            e.printStackTrace();
            return Rest.fail(msg,e);
        }
    }

    @Override
    public Rest getNoticeInfoList(Integer pageNum, Integer pageSize) {
        String msg = "获取公告";
        try {
            IPage<NoticeInfoTable> page = new Page<>(pageNum,pageSize);
            LambdaQueryWrapper<NoticeInfoTable> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.orderByDesc(NoticeInfoTable::getCreateDate);
            IPage<NoticeInfoTable> noticeInfoTableIPage = page(page,queryWrapper);
            List<NoticeInfoTable> noticeInfoTableList = noticeInfoTableIPage.getRecords();
            List<Map<String,Object>> mapList = noticeInfoTableList.stream().map(noticeInfoTable -> {
                Map<String,Object> map = new HashMap<>();
                map.put("noticeInfoUuid",noticeInfoTable.getNoticeInfoUuid());
                map.put("noticeInfoTitle",noticeInfoTable.getNoticeInfoTitle());
                map.put("noticeInfoIntro",noticeInfoTable.getNoticeInfoIntro());
                map.put("noticeInfoContent",noticeInfoTable.getNoticeInfoContent());
                map.put("noticeInfoAttachment",noticeInfoTable.getNoticeInfoAttachment());
                map.put("createDate", Datetime.format(noticeInfoTable.getCreateDate()));
                map.put("updateDate", Datetime.format(noticeInfoTable.getUpdateDate()));
                map.put("is_delete",noticeInfoTable.getIsDelete());
                return map;
            }).collect(Collectors.toList());
            Map<String,Object> map = new HashMap<>();
            map.put("total",noticeInfoTableIPage.getTotal());
            map.put("records",mapList);
            map.put("pageNum",noticeInfoTableIPage.getCurrent());
            map.put("pageSize",noticeInfoTableIPage.getSize());
            map.put("pages",noticeInfoTableIPage.getPages());
            return Rest.success(msg,map);
        } catch (Exception e) {
            e.printStackTrace();
            return Rest.fail(msg,e);
        }
    }
}
