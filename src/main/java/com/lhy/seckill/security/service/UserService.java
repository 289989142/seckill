package com.lhy.seckill.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhy.seckill.security.bean.SkUserEntity;
import com.lhy.seckill.security.mapper.SkUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;

/**
 * @ClassName UserService
 * @Description 用户相关业务
 * @Author lihengyu
 * @Date 2021/1/23 14:18
 * @Version 1.0
 */
@Service
public class UserService {
    @Autowired
    SkUserMapper skUserMapper;

    public SkUserEntity getUserByAccount(String account){
        QueryWrapper<SkUserEntity> wrapper = new QueryWrapper<SkUserEntity>();
        wrapper.eq("account",account);
        return skUserMapper.selectOne(wrapper);
    }
}
