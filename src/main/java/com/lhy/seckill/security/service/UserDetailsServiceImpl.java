package com.lhy.seckill.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserDetailServiceImpl
 * @Description userDetailService实现类
 * @Author lihengyu
 * @Date 2020/12/13 11:20
 * @Version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder pw;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1 查数据库是否存在用户 不存在抛出异常发
        if (!"admin".equals(username)) {
            throw new UsernameNotFoundException("用户名不存在!");
        }

        //2.解析密码
        String pwd = pw.encode("123");

        return new User(username,pwd, AuthorityUtils.commaSeparatedStringToAuthorityList(
                "admin,normal,ROLE_guest,ROLE_admin ,/insert,/delete"));

    }
}
