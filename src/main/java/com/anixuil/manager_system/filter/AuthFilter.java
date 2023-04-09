package com.anixuil.manager_system.filter;

import cn.hutool.json.JSONObject;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.utils.JwtUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthFilter implements Filter {

    //重写其中doFilter方法
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException{
        String url = ((HttpServletRequest)servletRequest).getRequestURI();
        JwtUtils jwtUtils = new JwtUtils();
        Rest rest = new Rest();
        try{
            if(url != null){
                //登录请求直接放行
                if("/anixuil/user/login".equals(url) || "/anixuil/user/register".equals(url) || url.contains("publicfile")){
                    filterChain.doFilter(servletRequest,servletResponse);
                    return;
                }
                else{
                    //其他请求拦截验证token
                    String token = String.valueOf(((HttpServletRequest)servletRequest).getHeaders("token"));
                    System.out.println(token);
                    if(StringUtils.isNotBlank(token)) {
                        //token验证结果
                        rest = jwtUtils.verifyToken(((HttpServletRequest) servletRequest), token);
                        if (rest.getCode() == 200) {
                            filterChain.doFilter(servletRequest,servletResponse);
                            return;
                        }
                    }
                    rest = Rest.fail("token验证","未检测到token,请先登录");
                }
            }
        }catch (Exception e){
            rest = new Rest(500,"检测异常,请联系管理员",e);
        }
        JSONObject jsonObject = new JSONObject(rest);
        System.out.println(rest);
        servletResponse.setContentType("application/json");
        //设置响应编码
        servletResponse.setCharacterEncoding("utf-8");
        //响应
        PrintWriter pw = servletResponse.getWriter();
        pw.write(jsonObject.toString());
        pw.flush();
        pw.close();
    }
}
