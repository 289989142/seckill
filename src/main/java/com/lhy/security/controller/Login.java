package com.lhy.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName Login
 * @Description 登录接口
 * @Author lihengyu
 * @Date 2020/12/12 20:36
 * @Version 1.0
 */
@Controller
public class Login {

    @RequestMapping("login")
    public String login(){
        System.out.println("执行登录方法");
        return "redirect:main.html";
    }
}
