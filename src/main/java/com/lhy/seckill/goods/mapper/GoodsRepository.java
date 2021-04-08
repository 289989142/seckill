package com.lhy.seckill.goods.mapper;

import com.lhy.seckill.goods.entity.SkGoodsEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @ClassName GoodsRepository
 * @Description ES操作
 * @Author lihengyu
 * @Date 2021/4/8 16:18
 * @Version 1.0
 */
@Component
public interface GoodsRepository extends ElasticsearchRepository<SkGoodsEntity,Integer> {
}
