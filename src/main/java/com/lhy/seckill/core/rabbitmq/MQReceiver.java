package com.lhy.seckill.core.rabbitmq;


import com.lhy.seckill.core.redis.RedisService;
import com.lhy.seckill.goods.entity.SkGoodsEntity;
import com.lhy.seckill.goods.service.GoodsService;
import com.lhy.seckill.order.entity.SkOrderEntity;
import com.lhy.seckill.order.service.OrderService;
import com.lhy.seckill.seckill.service.SeckillService;
import com.lhy.seckill.user.entity.SkUserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiangyunxiong on 2018/5/29.
 */
@Service
public class MQReceiver {

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);


    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SeckillService seckillService;

    @RabbitListener(queues=MQConfig.QUEUE)
    public void receive(String message){
        log.info("receive message:"+message);
        SeckillMessage m = RedisService.stringToBean(message, SeckillMessage.class);
        SkUserEntity user = m.getUser();
        Integer goodsId = m.getGoodsId();

        SkGoodsEntity goodsVo = goodsService.getGoods(goodsId);
        int stock = goodsVo.getStock();
        if(stock <= 0){
            return;
        }

        //判断重复秒杀
        SkOrderEntity order = orderService.getOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return;
        }

        //减库存 下订单 写入秒杀订单
        seckillService.seckill(user, goodsVo);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info(" topic  queue1 message:" + message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info(" topic  queue2 message:" + message);
    }
}
