package com.lhy.seckill.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhy.seckill.core.Constant;
import com.lhy.seckill.security.bean.SkUserEntity;
import com.lhy.seckill.security.mapper.SkUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ClassName RegisterService
 * @Description 注册服务
 * @Author lihengyu
 * @Date 2021/1/30 11:22
 * @Version 1.0
 */
@Service
public class RegisterService {

    @Autowired
    private SkUserMapper skUserMapper;

    /**
    * @Description //验证账户是否已经注册
    * @Param account
    * @return 验证通过返回true
    **/
    public boolean verifyAccount(String account){
        QueryWrapper<SkUserEntity> wrapper = new QueryWrapper<SkUserEntity>();
        wrapper.eq("account",account);
        SkUserEntity userEntity = skUserMapper.selectOne(wrapper);
        if (null != userEntity){
            return false;
        }
        return true;
    }

    public int register(String account,String nickName,String password,String avatar,String role){
        if (!verifyAccount(account)){
            return Constant.ACCOUNT_HAS_REGISTERED;
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        SkUserEntity entity = new SkUserEntity();
        entity.setAccount(account);
        entity.setNickname(nickName);
        entity.setPassword(bCryptPasswordEncoder.encode(password));
        entity.setAvatar(avatar);
        entity.setRole(role);
        entity.setCreateTime(LocalDateTime.now());
        entity.setLastTime(LocalDateTime.now());
        skUserMapper.insert(entity);

        return Constant.SUCCESS;
    }
}
