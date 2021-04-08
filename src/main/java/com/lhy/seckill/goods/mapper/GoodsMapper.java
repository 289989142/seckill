package com.lhy.seckill.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhy.seckill.goods.entity.SkGoodsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName GoodsMapper
 * @Description mapper
 * @Author lihengyu
 * @Date 2021/2/1 10:21
 * @Version 1.0
 */
@Mapper
public interface GoodsMapper extends BaseMapper<SkGoodsEntity> {
}
