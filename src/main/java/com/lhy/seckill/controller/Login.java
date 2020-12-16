package com.lhy.seckill.controller;

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
/*
    @RequestMapping("login")
    public String login(){
        System.out.println("执行登录方法");
        return "main";
    }
*/


    @RequestMapping("toMain")
    public String toMain(){
        System.out.println("执行登陆成功");
        return "main";
    }

    @RequestMapping("toError")
    public String toError(){
        System.out.println("执行登陆成功");
        return "error";
    }
}
