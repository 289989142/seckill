package com.lhy.seckill.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhy.seckill.order.entity.SkOrderEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @Description 订单mapper
 * @Author lihengyu
 * @Date 2021/4/27 14:41
 */
@Component
public interface OrderMapper extends BaseMapper<SkOrderEntity> {

    @Select("select * from  elastic_seckill.sk_order where userId = #{id}")
    SkOrderEntity getOrderById(Integer id);
}
