package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.service.impl.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("email")
public class MailController {
    @Autowired
    private MailService mailService;

    @GetMapping("getEmailCode")
    public Rest getEmailCode(@RequestParam String email){
        String msg = "邮箱验证码发送";
        String content = "Anixui提醒您，您的本次的验证码为：" + (new Random().nextInt(899999) + 100000);
        try{
            mailService.sendSimpleMail(email,msg,content);
            return Rest.success(msg,true);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

}
