package com.lhy.seckill.core.rabbitmq;


import com.lhy.seckill.user.entity.SkUserEntity;

/**
 * Created by jiangyunxiong on 2018/5/29.
 *
 * 消息体
 */
public class SeckillMessage {

    private SkUserEntity user;
    private long goodsId;

    public SkUserEntity getUser() {
        return user;
    }

    public void setUser(SkUserEntity user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
