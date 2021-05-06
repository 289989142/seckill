package com.lhy.seckill.security.config;

import com.lhy.seckill.security.handler.MyAccessDeniedHandler;
import com.lhy.seckill.security.handler.MyAuthenticationSuccessHandler;
import com.lhy.seckill.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @ClassName SecurityConfig
 * @Description 配置类
 * @Author lihengyu
 * @Date 2020/12/13 9:22
 * @Version 1.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;
    @Bean
    public PasswordEncoder getPw(){
        return new BCryptPasswordEncoder();
    }

    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //表单提交
        http.formLogin()
                //用户名密码的参数名
                .usernameParameter("username")
                .passwordParameter("password")
                //当发现是/login时认为是登录 必须和表单提交地址一样 去执行UserDetailsServiceImpl登录逻辑
                .loginProcessingUrl("/login")
                //自定义登陆页面
                .loginPage("/toLogin")
                //登陆成功后跳转页面 必须是post请求
//                .successForwardUrl("/")
                //登陆失败后跳转页面 post
                .failureForwardUrl("/loginFail")
                .successHandler(new MyAuthenticationSuccessHandler("/"));
        /*      .failureHandler(new MyAuthenticationFailureHandler("/myerror.html"));*/
        //退出登录 好像自动清除session
        http.logout()
                //自定义退出url
                .logoutUrl("/logout")
                //退出登录跳转页面
                .logoutSuccessUrl("/toLogin");

        http.authorizeRequests()
//                .antMatchers("/toLogin").permitAll()
//                .antMatchers("/toRegister").permitAll()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/register").permitAll()
                //admin角色才能访问admin下的请求
                .antMatchers("/admin/**").hasAnyAuthority("admin")
                //放行静态资源
                .antMatchers("/js/**","/css/**","picture/**").permitAll()
//                .anyRequest().authenticated();
        ;


        //异常处理
        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);
        //关闭csrf
        http.csrf().disable();
        //记住我
        http.rememberMe()
                //失效时间 s
                .tokenValiditySeconds(60*60*24*7)
                //前端参数
                .rememberMeParameter("remember-me")
                //登录逻辑
                .userDetailsService(userDetailsService)
                //持久层对象
                .tokenRepository(persistentTokenRepository);
      }
    /**
    * @Description //持久化token实现记住我
    * @Param []
    * @return org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
    **/
    @Bean
    public PersistentTokenRepository getPersistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //自动建表,第一次启动的时候需要,有表了要注释掉
        // jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

}
