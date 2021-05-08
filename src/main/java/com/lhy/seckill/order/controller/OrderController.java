package com.lhy.seckill.order.controller;

import com.lhy.seckill.goods.entity.SkGoodsEntity;
import com.lhy.seckill.goods.entity.SkGoodsSeckillEntity;
import com.lhy.seckill.goods.service.GoodsService;
import com.lhy.seckill.order.service.OrderService;
import com.lhy.seckill.security.entity.UserDetailImpl;
import com.lhy.seckill.user.entity.SkUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @Author lihengyu
 * @Date 2021/5/7 14:12
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;

    @PostMapping("order/goods")
    public String createOrder(Integer id,Double price,@RequestParam(defaultValue = "1",value = "num") Integer num){
        if ("anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            return "redirect:/toLogin";
        }
        //获取用户信息
        UserDetailImpl principal = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SkUserEntity user = new SkUserEntity(principal);
        boolean success = goodsService.reduceStock(id, num);
        if (success){
            orderService.createOrder(user,price,id,num,0);
        }else {
            return "库存不足.html";
        }
        return "createOrderSuccess";
    }

    @PostMapping("order/seckill")
    public String createSeckillOrder(Integer goodsId,Double seckillPrice, @RequestParam(defaultValue = "1",value = "num") Integer num){
        //获取用户信息
        UserDetailImpl principal = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SkUserEntity user = new SkUserEntity(principal);
        boolean success = goodsService.reduceStock(goodsId, num);
        if (success){
            orderService.createOrder(user,seckillPrice,goodsId,num,1);
        }else {
            return "库存不足.html";
        }
        return "createOrderSuccess";
    }
}
