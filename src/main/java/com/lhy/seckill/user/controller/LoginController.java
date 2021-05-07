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
public class LoginController {
    //TODO 订单前端搞一下
    //TODO 读懂秒杀流程 应用在本系统中
    //TODO 订单中心以及秒杀业务
    //TODO 完善用户管理后台
    //TODO footer优化一下
    //TODO 优化 图片上传 es同步mysql

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
