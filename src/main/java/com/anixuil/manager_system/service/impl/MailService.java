package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.UserTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service("mailService")
public class MailService {
    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private TemplateEngine templateEngine;


    //发送简单邮件 to：收件人  title：标题  code：验证码
    public void sendSimpleMail(String to,String title,String code){
        Context context = new Context();    //引入Template的Context
        context.setVariable("verifyCode", Arrays.asList(code.split("")));   //设置变量
        //第一个参数为模板的名称
        String process = templateEngine.process("EmailCode.html", context);
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(process,true);
        }catch (Exception e){
            e.printStackTrace();
        }

        mailSender.send(message);
    }


    public boolean sendAttachmentsMail(UserTable userTable, String title, String content, List<File> fileList){
        Context context = new Context();    //引入Template的Context
//        context.setVariable("verifyCode", Arrays.asList(content.split("")));   //设置变量
        context.setVariable("title", title);   //设置变量
        context.setVariable("content", content);   //设置变量
        context.setVariable("userName", userTable.getUserName());   //设置变量
        //第一个参数为模板的名称
        String process = templateEngine.process("EmailInform.html", context);
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(userTable.getUserEmail());
            helper.setSubject(title);
            helper.setText(process,true);
            String fileName = null;
            if(fileList != null){
                for (File file : fileList) {
                    fileName = MimeUtility.encodeText(file.getName(), "UTF-8", "B");
                    helper.addAttachment(fileName, file);
                }
            }
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}