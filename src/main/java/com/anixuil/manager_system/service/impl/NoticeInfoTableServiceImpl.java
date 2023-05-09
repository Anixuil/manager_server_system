package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.AttachmentTable;
import com.anixuil.manager_system.entity.NoticeInfoTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.mapper.NoticeInfoTableMapper;
import com.anixuil.manager_system.pojo.NoticeAll;
import com.anixuil.manager_system.service.AttachmentTableService;
import com.anixuil.manager_system.service.NoticeInfoTableService;
import com.anixuil.manager_system.utils.Datetime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Resource
    AttachmentTableService attachmentTableService;
    @Override
    public Rest addNoticeInfo(NoticeAll noticeAll) {
        String msg = "添加公告";
        try {
            NoticeInfoTable noticeInfoTable = new NoticeInfoTable();
            noticeInfoTable.setNoticeInfoTitle(noticeAll.getNoticeInfoTitle());
            noticeInfoTable.setNoticeInfoIntro(noticeAll.getNoticeInfoIntro());
            noticeInfoTable.setNoticeInfoContent(noticeAll.getNoticeInfoContent());
            noticeInfoTable.setNoticeInfoType(noticeAll.getNoticeInfoType());
            boolean result = save(noticeInfoTable);
            if(result){
                //添加附件
                List<AttachmentTable> attachmentList = noticeAll.getAttachmentList();
                //拿到公告的uuid
                LambdaQueryWrapper<NoticeInfoTable> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(NoticeInfoTable::getNoticeInfoTitle, noticeAll.getNoticeInfoTitle())
                        .eq(NoticeInfoTable::getNoticeInfoIntro, noticeAll.getNoticeInfoIntro())
                        .eq(NoticeInfoTable::getNoticeInfoContent, noticeAll.getNoticeInfoContent());
                String noticeInfoUuid = getOne(queryWrapper).getNoticeInfoUuid();
                if(attachmentList!=null && attachmentList.size()>0){
                    List<AttachmentTable> collect = attachmentList.stream().peek(attachmentTable -> {
                        attachmentTable.setNoticeInfoUuid(noticeInfoUuid);
                    }).collect(Collectors.toList());
                    boolean saveBatch = new AttachmentTableServiceImpl().saveBatch(collect);
                    if(saveBatch){
                        return Rest.success(msg,true);
                    }
                }else{
                    return Rest.success(msg,true);
                }
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
                attachmentTableService.remove(new LambdaQueryWrapper<AttachmentTable>().eq(AttachmentTable::getNoticeInfoUuid, noticeInfoTable.getNoticeInfoUuid()));
                return Rest.success(msg,true);
            }
            return Rest.fail(msg,false);
        } catch (Exception e) {
            e.printStackTrace();
            return Rest.fail(msg,e);
        }
    }

    @Override
    public Rest getNoticeInfoList(Integer pageNum, Integer pageSize, String noticeInfoTitle, String noticeInfoIntro, String noticeInfoType) {
        String msg = "获取公告";
        try {
            IPage<NoticeInfoTable> page = new Page<>(pageNum,pageSize);
            LambdaQueryWrapper<NoticeInfoTable> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.orderByDesc(NoticeInfoTable::getCreateDate)
                    .like(NoticeInfoTable::getNoticeInfoTitle,noticeInfoTitle)
                    .like(NoticeInfoTable::getNoticeInfoIntro,noticeInfoIntro)
                    .like(NoticeInfoTable::getNoticeInfoType,noticeInfoType);
            IPage<NoticeInfoTable> noticeInfoTableIPage = page(page,queryWrapper);
            List<NoticeInfoTable> noticeInfoTableList = noticeInfoTableIPage.getRecords();
            List<Map<String,Object>> mapList = noticeInfoTableList.stream().map(noticeInfoTable -> {
                Map<String,Object> map = new HashMap<>();
                map.put("noticeInfoUuid",noticeInfoTable.getNoticeInfoUuid());
                map.put("noticeInfoTitle",noticeInfoTable.getNoticeInfoTitle());
                map.put("noticeInfoIntro",noticeInfoTable.getNoticeInfoIntro());
                map.put("noticeInfoContent",noticeInfoTable.getNoticeInfoContent());
                map.put("noticeInfoType",noticeInfoTable.getNoticeInfoType());
                map.put("createDate", Datetime.format(noticeInfoTable.getCreateDate()));
                map.put("updateDate", Datetime.format(noticeInfoTable.getUpdateDate()));
                map.put("is_delete",noticeInfoTable.getIsDelete());
                //获取附件
                LambdaQueryWrapper<AttachmentTable> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(AttachmentTable::getNoticeInfoUuid,noticeInfoTable.getNoticeInfoUuid());
                List<AttachmentTable> attachmentTableList = attachmentTableService.list(queryWrapper1);
                map.put("attachmentList",attachmentTableList);
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

    //获取公告详情
    @Override
    public Rest getOne(String noticeInfoUuid) {
        String msg = "获取公告详情";
        try {
            NoticeInfoTable noticeInfoTable1 = getById(noticeInfoUuid);
            Map<String,Object> map = new HashMap<>();
            map.put("noticeInfoUuid",noticeInfoTable1.getNoticeInfoUuid());
            map.put("noticeInfoTitle",noticeInfoTable1.getNoticeInfoTitle());
            map.put("noticeInfoIntro",noticeInfoTable1.getNoticeInfoIntro());
            map.put("noticeInfoContent",noticeInfoTable1.getNoticeInfoContent());
            map.put("createDate", Datetime.format(noticeInfoTable1.getCreateDate(),"yyyy年MM月dd日"));
            map.put("updateDate", Datetime.format(noticeInfoTable1.getUpdateDate(),"yyyy年MM月dd日"));
            map.put("is_delete",noticeInfoTable1.getIsDelete());
            //获取附件
            LambdaQueryWrapper<AttachmentTable> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(AttachmentTable::getNoticeInfoUuid,noticeInfoTable1.getNoticeInfoUuid());
            List<AttachmentTable> attachmentTableList = attachmentTableService.list(queryWrapper1);
            map.put("attachmentList",attachmentTableList);
            return Rest.success(msg,map);
        } catch (Exception e) {
            e.printStackTrace();
            return Rest.fail(msg,e);
        }
    }

}
