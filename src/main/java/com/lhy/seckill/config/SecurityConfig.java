package com.lhy.seckill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @ClassName SecurityConfig
 * @Description 配置类
 * @Author lihengyu
 * @Date 2020/12/13 9:22
 * @Version 1.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder getPw(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //表单提交
        http.formLogin()
                //当发现是/login时认为是登录 必须和表单提交地址一样 去执行UserDetailsServiceImpl登录逻辑
                .loginProcessingUrl("/login")
                //自定义登陆页面
                .loginPage("/login.html")
                //登陆成功后跳转页面 必须是post请求
                .successForwardUrl("/toMain")
                //登陆失败后跳转页面 post
                .failureForwardUrl("/toError");

        http.authorizeRequests()
                //login.html 不需要被认证
                .antMatchers("/login.html").permitAll()
                //错误页面不需要拦截
                .antMatchers("/error.html").permitAll()
                //所有请求都需要被认证
                .anyRequest().authenticated();

        //关闭csrf
        http.csrf().disable();
    }
}
