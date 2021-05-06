package com.lhy.seckill.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    //TODO 秒杀商品列表  秒杀商品详情
    //TODO 用户管理后台
    //TODO 后台管理的sql搜索功能
    //TODO 优化 图片上传 es同步mysql

    @RequestMapping("toMain")
    public String toMain(){
        System.out.println("执行登陆成功");
        return "main";
    }

    @RequestMapping("loginFail")
    public String loginFail(Model model){
        System.out.println("执行登陆失败");
        model.addAttribute("message","登陆失败请重试");
        return "login";
    }

    @RequestMapping("toLogin")
    private String showLogin(){
        return "login";
    }
}
