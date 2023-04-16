package com.anixuil.manager_system.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebUtil {
    //将字符串渲染到客户端
    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
