package com.lhy.seckill.goods.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhy.seckill.goods.entity.SkGoodsSeckillEntity;
import com.lhy.seckill.goods.mapper.SeckillGoodsMapper;
import com.lhy.seckill.security.bean.SkGoodsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @ClassName GoodsService
 * @Description 秒杀商品服务
 * @Author lihengyu
 * @Date 2021/2/1 10:24
 * @Version 1.0
 */
@Service
public class SeckillGoodsService {

    @Autowired(required = false)
    private  SeckillGoodsMapper seckillGoodsMapper;

    public IPage<SkGoodsSeckillEntity> getGoodsPage(int pageNum , int pageSize){
        Page<SkGoodsSeckillEntity> page = new Page<>(pageNum,pageSize);
        return seckillGoodsMapper.selectPage(page, null);
    }

    public int addGoods(SkGoodsSeckillEntity entity){
        entity.setCreateTime(LocalDateTime.now());
        entity.setLastTime(LocalDateTime.now());
        return seckillGoodsMapper.insert(entity);
    }

    public int deleteGoods(Long id){
        return seckillGoodsMapper.deleteById(id);
    }

    public SkGoodsSeckillEntity getGoods(Long id){
        return seckillGoodsMapper.selectById(id);
    }


    public int updateGoods(SkGoodsSeckillEntity entity){
        entity.setLastTime(LocalDateTime.now());
        return seckillGoodsMapper.updateById(entity);
    }
}
