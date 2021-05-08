package com.lhy.seckill.order.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhy.seckill.core.redis.OrderKey;
import com.lhy.seckill.core.redis.RedisService;
import com.lhy.seckill.goods.service.GoodsService;
import com.lhy.seckill.order.controller.param.OrderQueryParam;
import com.lhy.seckill.order.entity.SkOrderEntity;
import com.lhy.seckill.order.mapper.OrderMapper;
import com.lhy.seckill.user.entity.SkUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public Page<SkOrderEntity> getOrderByUserId(OrderQueryParam param) {
        QueryWrapper<SkOrderEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("userId",param.getUserId());
        Page<SkOrderEntity> page = new Page<>(param.getPageNum(),param.getPageSize());
        return orderMapper.selectPage(page,wrapper);
    }


    public SkOrderEntity createOrder(SkUserEntity user,Double price, Integer goodsId,Integer num,Integer isSeckill) {
        SkOrderEntity orderInfo = new SkOrderEntity();
        orderInfo.setCreateTime(new Date());
        orderInfo.setGoodsCount(num);
        orderInfo.setGoodsId(goodsId);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        orderInfo.setIsSeckill(isSeckill);
        orderInfo.setLastTime(new Date());
        orderInfo.setPrice(price);
        orderMapper.insert(orderInfo);
        //多传一个price过来
        redisService.set(OrderKey.getSeckillOrderByUidGid, "" + user.getId() + "_" + goodsId, orderInfo);
        return orderInfo;
    }

    public void delete(Integer id){
        orderMapper.deleteById(id);
    }

    public void update(SkOrderEntity entity){
        orderMapper.updateById(entity);
    }

}
