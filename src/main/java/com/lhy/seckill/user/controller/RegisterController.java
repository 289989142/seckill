package com.lhy.seckill.user.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.lhy.seckill.core.response.CodeMsg;
import com.lhy.seckill.security.service.MailService;
import com.lhy.seckill.security.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * @ClassName SignUp
 * @Description 注册
 * @Author lihengyu
 * @Date 2020/12/6 15:25
 * @Version 1.0
 */
@Controller
public class RegisterController {


    @Autowired
    private RegisterService registerService;
    @Autowired
    private MailService mailService;
    @Autowired
    private DefaultKaptcha captchaProducer;

    private static final String MAIL_VERIFY_SESSION_KEY = "mailVerify";
    private static final String VERIFY_CODE_SESSION_KEY = "verifyCode";
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
        request.getSession().setAttribute(MAIL_VERIFY_SESSION_KEY,String.valueOf(random));
        return "data: 邮件已经发送";
    }


    @RequestMapping("/register")
    public String register(Model model, HttpServletRequest request, String mailVerify, String email, String password, String verifyCode){

        if (!imgVerifyControllerDefaultKaptcha(request,verifyCode)){
            model.addAttribute("message","图形验证码错误！请重试！");
            return "register";
        }

        if (!(mailVerify).equals(request.getSession().getAttribute(MAIL_VERIFY_SESSION_KEY))){
            model.addAttribute("message","邮箱验证码错误！请重试！");
            return "register";
        }
        //TODO 修改昵称 头像 角色
        int result = registerService.register(email, "测试", password, "测试", "guest");

        if (CodeMsg.ACCOUNT_HAS_REGISTERED.getCode() == result){
            model.addAttribute("message","账户已经注册过！请登录！");
            return "register";
        }

        model.addAttribute("message","账户注册成功！请登录！");
        return "register";
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
            httpServletRequest.getSession().setAttribute(VERIFY_CODE_SESSION_KEY, createText);
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
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    /**
     * 验证的方法imgVerifyControllerDefaultKaptcha
     * @return
     */
    @RequestMapping("/imgVerifyControllerDefaultKaptcha")
    public boolean imgVerifyControllerDefaultKaptcha(HttpServletRequest httpServletRequest,String parameter){
        String captchaId =String.valueOf(httpServletRequest.getSession().getAttribute(VERIFY_CODE_SESSION_KEY));
        if (!captchaId.equals(parameter)) {
            return false;
        } else {
            return true;
        }
    }


}
