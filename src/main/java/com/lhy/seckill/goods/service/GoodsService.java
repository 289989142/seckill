package com.lhy.seckill.goods.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhy.seckill.goods.mapper.GoodsMapper;
import com.lhy.seckill.security.bean.SkGoodsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @ClassName GoodsService
 * @Description 商品服务
 * @Author lihengyu
 * @Date 2021/2/1 10:24
 * @Version 1.0
 */
@Service
public class GoodsService {

    @Autowired(required = false)
    private  GoodsMapper goodsMapper;

    public IPage<SkGoodsEntity> getGoodsPage(int pageNum , int pageSize){
        Page<SkGoodsEntity> page = new Page<>(pageNum,pageSize);
        return goodsMapper.selectPage(page, null);
    }

    public int addGoods(SkGoodsEntity entity){
        entity.setCreateTime(LocalDateTime.now());
        entity.setLastTime(LocalDateTime.now());
        return goodsMapper.insert(entity);
    }

    public int deleteGoods(Long id){
        return goodsMapper.deleteById(id);
    }

    public SkGoodsEntity getGoods(Long id){
        return goodsMapper.selectById(id);
    }


    public int updateGoods(SkGoodsEntity entity){
        entity.setLastTime(LocalDateTime.now());
        return goodsMapper.updateById(entity);
    }
}
