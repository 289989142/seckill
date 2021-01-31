package com.lhy.seckill.security.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.lhy.seckill.core.Constant;
import com.lhy.seckill.security.service.MailService;
import com.lhy.seckill.security.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;

/**
 * @ClassName SignUp
 * @Description 注册
 * @Author lihengyu
 * @Date 2020/12/6 15:25
 * @Version 1.0
 */
@Controller
public class Register {


    @Autowired
    private RegisterService registerService;
    @Autowired
    private MailService mailService;
    @Autowired
    private DefaultKaptcha captchaProducer;
    /**
    * @Description // 跳转到注册页面
    * @Param
    * @return
    **/
    @RequestMapping("/toRegister")
    public String toRegister(){
        return "register";
    }

    @PostMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(String email,HttpServletRequest request){
        int random = (int)(Math.random()*(999999-100000)+100000);
        mailService.sendSimpleMail(email,"您正在注册我们的网站","您的验证码为:"+random);
        request.getSession().setAttribute("mailVerify",String.valueOf(random));
        System.out.println(String.valueOf(random));
        return "data: 邮件已经发送";
    }


    @RequestMapping("/register")
    public String register(HttpServletRequest request,String mailVerify,String email,String password,String verifyCode){
        System.out.println(email);
        System.out.println(password);
        System.out.println(mailVerify);
        System.out.println(verifyCode);

        if (!imgvrifyControllerDefaultKaptcha(request,verifyCode)){
            return "/error/error";
        }

        System.out.println(1);

        System.out.println((String) request.getSession().getAttribute("mailVerify"));
        if (!((String) request.getSession().getAttribute("mailVerify")).equals(mailVerify)){
            return "/error/error";
        }
        //调用注册业务
        System.out.println(2);
        int result = registerService.register(email, "测试", password, "测试", "guest");

        System.out.println(3);
        if (Constant.ACCOUNT_HAS_REGISTERED == result){
            return "/error/error";
        }

        System.out.println(4);
        return "login";
    }
    /**
     * 获取验证码 的 请求路径
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws Exception
     */
    @RequestMapping("/defaultKaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception{
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = captchaProducer.createText();
            httpServletRequest.getSession().setAttribute("verifyCode", createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = captchaProducer.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    /**
     * 验证的方法
     * @return
     */
    @RequestMapping("/imgvrifyControllerDefaultKaptcha")
    public boolean imgvrifyControllerDefaultKaptcha(HttpServletRequest httpServletRequest,String parameter){
        ModelAndView andView = new ModelAndView();
        String captchaId = (String) httpServletRequest.getSession().getAttribute("verifyCode");
        System.out.println("Session  verifyCode "+captchaId+" form verifyCode "+parameter);

        if (!captchaId.equals(parameter)) {
            return false;
        } else {
            return true;
        }
    }
//    public ModelAndView imgvrifyControllerDefaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
//        ModelAndView andView = new ModelAndView();
//        String captchaId = (String) httpServletRequest.getSession().getAttribute("vrifyCode");
//        String parameter = httpServletRequest.getParameter("vrifyCode");
//        System.out.println("Session  vrifyCode "+captchaId+" form vrifyCode "+parameter);
//
//        if (!captchaId.equals(parameter)) {
//            andView.addObject("info", "错误的验证码");
//            andView.setViewName("index");
//        } else {
//            andView.addObject("info", "登录成功");
//            andView.setViewName("success");
//        }
//        return andView;
//    }

}
