package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.entity.NoticeInfoTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.pojo.NoticeAll;
import com.anixuil.manager_system.service.NoticeInfoTableService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("noticeInfo")
public class NoticeInfoController {
    @Resource
    NoticeInfoTableService noticeInfoTableService;

    @PostMapping("addNoticeInfo")
    public Rest addNoticeInfo(@RequestBody NoticeAll noticeAll){
        return noticeInfoTableService.addNoticeInfo(noticeAll);
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
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String noticeInfoTitle,
            @RequestParam(defaultValue = "") String noticeInfoIntro,
            @RequestParam(defaultValue = "") String noticeInfoType
    ){
        return noticeInfoTableService.getNoticeInfoList(pageNum,pageSize,noticeInfoTitle,noticeInfoIntro,noticeInfoType);
    }

    //公开的获取公告列表
    @GetMapping("getNoticeList")
    public Rest getNoticeList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String noticeInfoTitle,
            @RequestParam(defaultValue = "") String noticeInfoIntro,
            @RequestParam(defaultValue = "") String noticeInfoType
    ){
        return noticeInfoTableService.getNoticeInfoList(pageNum,pageSize,noticeInfoTitle,noticeInfoIntro,noticeInfoType);
    }

    //获取公告详情
    @GetMapping("getOne")
    public Rest getOne(@RequestParam String noticeInfoUuid){
        return noticeInfoTableService.getOne(noticeInfoUuid);
    }

}
