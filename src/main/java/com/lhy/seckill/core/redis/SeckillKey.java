package com.lhy.seckill.core.redis;

/**
 * Created by lihengyu on 2021/4/27.
 */
public class SeckillKey extends BasePrefix {
    private SeckillKey(String prefix) {
        super(prefix);
    }

    public static SeckillKey isGoodsOver = new SeckillKey("go");
}
