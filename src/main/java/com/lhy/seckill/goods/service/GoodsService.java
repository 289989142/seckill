package com.lhy.seckill.goods.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhy.seckill.goods.entity.ElasticSearchGoodsEntity;
import com.lhy.seckill.goods.mapper.GoodsMapper;
import com.lhy.seckill.goods.entity.SkGoodsEntity;
import com.lhy.seckill.goods.mapper.GoodsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;


/**
 * @ClassName GoodsService
 * @Description 商品服务
 * @Author lihengyu
 * @Date 2021/2/1 10:24
 * @Version 1.0
 */
@Service
public class GoodsService {

    //TODO 做es查询  优化图片上传

    @Autowired(required = false)
    private  GoodsMapper goodsMapper;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private GoodsRepository repository;

    public IPage<SkGoodsEntity> getGoodsPage(int pageNum , int pageSize){
        Page<SkGoodsEntity> page = new Page<>(pageNum,pageSize);
        return goodsMapper.selectPage(page, null);
    }

    @Transactional(rollbackFor = Exception.class)
    public int addGoods(SkGoodsEntity entity){
        entity.setCreateTime(LocalDateTime.now());
        entity.setLastTime(LocalDateTime.now());
        int result = goodsMapper.insert(entity);
        entity.setId(entity.getId());
        repository.save(convert(entity));
        return result;
    }
    @Transactional(rollbackFor = Exception.class)
    public int deleteGoods(Integer id){
        repository.deleteById(id);
        return goodsMapper.deleteById(id);
    }

    public SkGoodsEntity getGoods(Integer id){
        return goodsMapper.selectById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateGoods(SkGoodsEntity entity){
        repository.deleteById(entity.getId());
        repository.save(convert(entity));
        entity.setLastTime(LocalDateTime.now());
        return goodsMapper.updateById(entity);
    }


    private ElasticSearchGoodsEntity convert(SkGoodsEntity entity){
        ElasticSearchGoodsEntity elasticSearchGoodsEntity = new ElasticSearchGoodsEntity();
        elasticSearchGoodsEntity.setId(entity.getId());
        elasticSearchGoodsEntity.setName(entity.getName());
        elasticSearchGoodsEntity.setPrice(entity.getPrice());
        elasticSearchGoodsEntity.setStock(entity.getStock());
        elasticSearchGoodsEntity.setDescription(entity.getDescription());
        elasticSearchGoodsEntity.setType(entity.getType());
        elasticSearchGoodsEntity.setCreateTime(entity.getCreateTime());
        elasticSearchGoodsEntity.setLastTime(entity.getLastTime());

        return elasticSearchGoodsEntity;
    }

}
