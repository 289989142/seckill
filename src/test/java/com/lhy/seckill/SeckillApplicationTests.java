package com.lhy.seckill;

import com.lhy.seckill.security.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class SeckillApplicationTests {

    @Test
    public void contextLoads(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
        boolean matches = bCryptPasswordEncoder.matches("123456", encode);
        System.out.println(matches);
        boolean matches1 = bCryptPasswordEncoder.matches("1234567", encode);
        System.out.println(matches1);
    }

    @Test
    public void test(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("li289989142");
        System.out.println(encode);
    }

    @Autowired
    private MailService mailService;

    @Test
    public void testSimpleMail() throws Exception {
        mailService.sendSimpleMail("13941715477@163.com","测试发送简单邮件"," Hello, this is simple mail!");
    }

    @Test
    public void testHtmlMail() throws Exception {
        String content="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlMail("13941715477@163.com","测试发送html邮件",content);
    }

    @Test
    public void sendAttachmentsMail() {
        String filePath="d:\\BaiduNetdiskDownload\\spring-boot-book.pdf";
        mailService.sendAttachmentsMail("13941715477@163.com", "主题: 您有一封邮件待查看", "附上example文件,请查收! ", filePath);
    }

    @Test
    public void sendInlineResourceMail() {
        String resId = "dan.J";
        String content="<html>\n" +
                "<center><body>\n" +
                "这是一封有图片的邮件哦! O(∩_∩)O <img src=\'cid:" + resId + "\' >\n" +
                "</body></center>\n" +
                "</html>";
        String imgPath = "C:\\Users\\Amanda\\Pictures\\Camera Roll\\test.jpg";

        mailService.sendInlineResourceMail("13941715477@163.com", "主题: 您有一封邮件待查看", content, imgPath, resId);
    }
}