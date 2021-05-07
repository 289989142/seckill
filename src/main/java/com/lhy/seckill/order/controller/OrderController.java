package com.lhy.seckill.order.controller;

import com.lhy.seckill.goods.entity.SkGoodsEntity;
import com.lhy.seckill.order.service.OrderService;
import com.lhy.seckill.user.entity.SkUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description
 * @Author lihengyu
 * @Date 2021/5/7 14:12
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("createOrder")
    public String createOrder(SkUserEntity user, SkGoodsEntity goods, Integer num){
        orderService.createOrder(user,goods,1);
        return "createOrderSuccess";
    }
}
