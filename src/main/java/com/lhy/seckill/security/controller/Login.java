package com.lhy.seckill.security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName Login
 * @Description
 * @Author lihengyu
 * @Date 2020/12/6 15:26
 * @Version 1.0
 */
@Controller
public class Login {

    @Secured({"ROLE_guest","ROLE_admin"})
    @RequestMapping("toMain")
    public String toMain(){
        System.out.println("执行登陆成功");
        return "main";
    }

    @RequestMapping("toError")
    public String toError(){
        System.out.println("执行登陆成功");
        return "myerror";
    }

    @RequestMapping("demo")
    private String toDemo(){
        return "demo";
    }

    @RequestMapping("toLogin")
    private String showLogin(){
        return "login";
    }
}
