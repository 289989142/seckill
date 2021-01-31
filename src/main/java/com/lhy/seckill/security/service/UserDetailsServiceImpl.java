package com.lhy.seckill.security.service;

import com.lhy.seckill.security.bean.SkUserEntity;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder pw;

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        System.out.println("test");

        //1 查数据库是否存在用户 不存在抛出异常发
        SkUserEntity user = userService.getUserByAccount(account);
        if (null == user) {
            throw new UsernameNotFoundException("用户不存在!");
        }

        //角色权限
        String role = "ROLE_"+user.getRole();

        return new User(user.getAccount(),user.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(role));
    }
}
