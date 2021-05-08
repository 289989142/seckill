package com.lhy.seckill.core.rabbitmq;


import com.lhy.seckill.user.entity.SkUserEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by jiangyunxiong on 2018/5/29.
 *
 * 消息体
 */
@Getter
@Setter
public class SeckillMessage {

    private SkUserEntity user;
    private Integer goodsId;

}
