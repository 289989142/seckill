package com.lhy.seckill.core.redis;

/**
 * Created by lihengyu on 2021/4/27.
 */
public class OrderKey extends BasePrefix {

    public OrderKey(String prefix) {
        super(prefix);
    }
    public static OrderKey getSeckillOrderByUidGid = new OrderKey("seckill");
}
