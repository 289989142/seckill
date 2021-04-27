package com.lhy.seckill.order.service;

import com.lhy.seckill.goods.entity.SkGoodsEntity;
import com.lhy.seckill.order.entity.SkOrderEntity;
import com.lhy.seckill.user.entity.SkUserEntity;

/**
 * @author lihengyu
 */
public interface OrderService {
    /** @Description 根据用户id获取订单列表
    * @Param [orderId]
    * @return com.lhy.seckill.order.entity.SkOrderEntity
    **/
    SkOrderEntity getOrderById(Integer orderId);

    /** @Description 创建订单
    * @Param [user, goods]
    * @return com.lhy.seckill.order.entity.SkOrderEntity
    **/
    SkOrderEntity createOrder(SkUserEntity user, SkGoodsEntity goods);
}
