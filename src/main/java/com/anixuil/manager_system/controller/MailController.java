package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.entity.CandidateTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.UserTable;
import com.anixuil.manager_system.service.CandidateTableService;
import com.anixuil.manager_system.service.UserTableService;
import com.anixuil.manager_system.service.impl.MailService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("email")
public class MailController {
    @Resource
    private MailService mailService;

    @Resource
    private CandidateTableService candidateTableService;

    @Resource
    private UserTableService userTableService;

    @GetMapping("getEmailCode")
    public Rest getEmailCode(@RequestParam String email){
        String msg = "邮箱验证码发送";
//        String content = "【中南林业科技大学涉外学院研究生招生处】提醒您，您的本次的验证码为：" + (new Random().nextInt(899999) + 100000) + "，请在5分钟内完成验证。";
        String code = String.valueOf(new Random().nextInt(899999) + 100000);
        try{
            mailService.sendSimpleMail(email,msg,code);
            return Rest.success(msg,true);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    @PostMapping("sendEmail")
    public Rest sendEmail(@RequestBody Map<String,Object> emailOption){
        String msg = "邮件通知发送";
        String candidateStatus = emailOption.get("emailType").toString(); //获取发送对象类型
        try{
        LambdaQueryWrapper<CandidateTable> wrapper = new LambdaQueryWrapper<>();
        //获取发送对象考生的userUuid，用于获取考生的邮箱
        wrapper.eq(CandidateTable::getCandidateStatus,candidateStatus)
                .select(CandidateTable::getUserUuid);
        List<UserTable> userTableList = candidateTableService.list(wrapper).stream().map(candidateTable -> {
            LambdaQueryWrapper<UserTable> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(UserTable::getUserUuid,candidateTable.getUserUuid());
            return userTableService.getOne(wrapper1);
        }).collect(Collectors.toList());
        //获取考生的邮箱
//        List<String> emailList = userTableList.stream().map(UserTable::getUserEmail).collect(Collectors.toList());
        String emailTitle = emailOption.get("emailTitle").toString(); //获取邮件标题
        String emailContent = emailOption.get("emailContent").toString(); //获取邮件内容
        AtomicInteger resultOk = new AtomicInteger();
        AtomicInteger resultError = new AtomicInteger();
        List<String> emailErrorList = new ArrayList<>();
        Map<String,Object> res = new HashMap<>();
            userTableList.forEach(userTable -> {
                boolean result = mailService.sendAttachmentsMail(userTable,emailTitle,emailContent,null);
                if(result){
                    resultOk.getAndIncrement();
                }else{
                    resultError.getAndIncrement();
                    emailErrorList.add(userTable.getUserName());
                }
            });
            res.put("resultOk",resultOk);
            res.put("resultError",resultError);
            res.put("emailErrorList",emailErrorList);
            return Rest.success(msg,res);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

}
