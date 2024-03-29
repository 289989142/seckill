package com.lhy.seckill.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @ClassName MyAuthenticationSuccessHandler
 * @Description 自定义登陆成功handler
 * @Author lihengyu
 * @Date 2020/12/18 21:33
 * @Version 1.0
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private String url;

    public MyAuthenticationSuccessHandler(String url) {
        this.url = url;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String name = authentication.getName();
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("username",name);
        httpServletResponse.sendRedirect(url);
    }

}
