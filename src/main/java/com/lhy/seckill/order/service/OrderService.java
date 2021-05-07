package com.lhy.seckill.order.service;

import com.lhy.seckill.core.redis.OrderKey;
import com.lhy.seckill.core.redis.RedisService;
import com.lhy.seckill.goods.entity.SkGoodsEntity;
import com.lhy.seckill.goods.service.GoodsService;
import com.lhy.seckill.order.entity.SkOrderEntity;
import com.lhy.seckill.order.mapper.OrderMapper;
import com.lhy.seckill.user.entity.SkUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description 订单服务
 * @Author lihengyu
 * @Date 2021/4/27 15:16
 */
@Service
public class OrderService{

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;
    public SkOrderEntity getOrderByUserIdGoodsId(long userId, long goodsId) {
        return redisService.get(OrderKey.getSeckillOrderByUidGid, "" + userId + "_" + goodsId, SkOrderEntity.class);
    }


    public SkOrderEntity getOrderById(Integer orderId) {
        return orderMapper.getOrderById(orderId);
    }


    public SkOrderEntity createOrder(SkUserEntity user, SkGoodsEntity goods,Integer num) {
        SkOrderEntity orderInfo = new SkOrderEntity();
        orderInfo.setCreateTime(new Date());
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        orderMapper.insert(orderInfo);

        redisService.set(OrderKey.getSeckillOrderByUidGid, "" + user.getId() + "_" + goods.getId(), orderInfo);
        return orderInfo;
    }

    public void delete(Integer id){
        orderMapper.deleteById(id);
    }

    public void update(SkOrderEntity entity){
        orderMapper.updateById(entity);
    }

}
