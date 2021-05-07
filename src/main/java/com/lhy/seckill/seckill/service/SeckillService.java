package com.lhy.seckill.seckill.service;

import com.lhy.seckill.core.redis.RedisService;
import com.lhy.seckill.core.redis.SeckillKey;
import com.lhy.seckill.goods.entity.SkGoodsEntity;
import com.lhy.seckill.goods.service.GoodsService;
import com.lhy.seckill.order.entity.SkOrderEntity;
import com.lhy.seckill.order.service.OrderService;
import com.lhy.seckill.user.entity.SkUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author lihengyu
 * @Date 2021/5/7 16:20
 */
@Service
public class SeckillService {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    //保证这三个操作，减库存 下订单 写入秒杀订单是一个事物
    @Transactional
    public SkOrderEntity seckill(SkUserEntity user, SkGoodsEntity goods){
        //减库存
        boolean success = goodsService.reduceStock(goods.getId(),1);
        if (success){
            //下订单 写入秒杀订单
            return orderService.createOrder(user, goods,1);
        }else {
            setGoodsOver(Long.valueOf(goods.getId()));
            return null;
        }
    }

    public long getSeckillResult(long userId, long goodsId){
        SkOrderEntity order = orderService.getOrderByUserIdGoodsId(userId, goodsId);
        if (order != null){
            return order.getId();
        }else{
            boolean isOver = getGoodsOver(goodsId);
            if(isOver) {
                return -1;
            }else {
                return 0;
            }
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(SeckillKey.isGoodsOver, ""+goodsId, true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(SeckillKey.isGoodsOver, ""+goodsId);
    }
}
