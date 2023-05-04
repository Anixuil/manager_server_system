package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.entity.NoticeInfoTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.service.NoticeInfoTableService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("noticeInfo")
public class NoticeInfoController {
    @Resource
    NoticeInfoTableService noticeInfoTableService;

    @PostMapping("addNoticeInfo")
    public Rest addNoticeInfo(@RequestBody NoticeInfoTable noticeInfoTable){
        return noticeInfoTableService.addNoticeInfo(noticeInfoTable);
    }

    @PutMapping("updateNoticeInfo")
    public Rest updateNoticeInfo(@RequestBody NoticeInfoTable noticeInfoTable){
        return noticeInfoTableService.updateNoticeInfo(noticeInfoTable);
    }

    @DeleteMapping("deleteNoticeInfo")
    public Rest deleteNoticeInfo(@RequestBody NoticeInfoTable noticeInfoTable){
        return noticeInfoTableService.deleteNoticeInfo(noticeInfoTable);
    }

    //获取公告列表
    @GetMapping("getNoticeInfoList")
    public Rest getNoticeInfoList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        return noticeInfoTableService.getNoticeInfoList(pageNum,pageSize);
    }
}
