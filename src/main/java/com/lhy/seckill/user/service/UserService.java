package com.lhy.seckill.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhy.seckill.goods.entity.SkGoodsEntity;
import com.lhy.seckill.user.controller.param.UserQueryParam;
import com.lhy.seckill.user.entity.SkUserEntity;
import com.lhy.seckill.user.mapper.SkUserMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /** @Description 通过账号获取用户
    * @Param [account]
    * @return com.lhy.seckill.user.entity.SkUserEntity
    **/
    public SkUserEntity getUserByAccount(String account){
        QueryWrapper<SkUserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("account",account);
        return skUserMapper.selectOne(wrapper);
    }

    /** @Description 通过条件分页查询
    * @Param [queryParam]
    * @return com.baomidou.mybatisplus.core.metadata.IPage<com.lhy.seckill.user.entity.SkUserEntity>
    **/
    public IPage<SkUserEntity> getPageByParam(UserQueryParam queryParam){
        QueryWrapper<SkUserEntity> wrapper = new QueryWrapper<>();
        Page<SkUserEntity> page = new Page<>(queryParam.getPageNum(),queryParam.getPageSize());
        if (null!=queryParam.getId()){
            wrapper.eq("id",queryParam.getId());
        }
        if (Strings.isNotBlank(queryParam.getAccount())){
            wrapper.eq("account",queryParam.getAccount());
        }
        if (Strings.isNotBlank(queryParam.getNickname())){
            wrapper.eq("nickname",queryParam.getNickname());
        }
        if (Strings.isNotBlank(queryParam.getRole())){
            wrapper.eq("role",queryParam.getRole());
        }
        return skUserMapper.selectPage(page,wrapper);
    }

    public void delete(Integer id){
        QueryWrapper<SkUserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        skUserMapper.delete(wrapper);
    }

    public void insert(SkUserEntity userEntity){
        skUserMapper.insert(userEntity);
    }

    public void update(SkUserEntity userEntity){
        skUserMapper.updateById(userEntity);
    }
}
