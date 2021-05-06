package com.lhy.seckill.goods.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhy.seckill.core.exception.GlobalException;
import com.lhy.seckill.core.response.CodeMsg;
import com.lhy.seckill.goods.entity.ElasticSearchGoodsEntity;
import com.lhy.seckill.goods.entity.SkGoodsEntity;
import com.lhy.seckill.goods.mapper.GoodsMapper;
import com.lhy.seckill.goods.mapper.GoodsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Wrapper;
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
    @Autowired
    private  GoodsMapper goodsMapper;

    @Autowired
    ElasticsearchRestTemplate template;
    @Autowired
    private GoodsRepository repository;

    public IPage<SkGoodsEntity> getGoodsPage(int pageNum , int pageSize){
        Page<SkGoodsEntity> page = new Page<>(pageNum,pageSize);
        return goodsMapper.selectPage(page, null);
    }

    public IPage<SkGoodsEntity> getGoodsPage(int pageNum , int pageSize , String goodsName){
        QueryWrapper<SkGoodsEntity> wrapper = new QueryWrapper<>();
        wrapper.like("name",goodsName);
        Page<SkGoodsEntity> page = new Page<>(pageNum,pageSize);
        return goodsMapper.selectPage(page, wrapper);
    }

    /** @Description mysql es同步新增数据
    * @Param [entity]
    * @return int
    **/
    @Transactional(rollbackFor = Exception.class)
    public int addGoods(SkGoodsEntity entity){
        entity.setCreateTime(LocalDateTime.now());
        entity.setLastTime(LocalDateTime.now());
        int result = goodsMapper.insert(entity);
        entity.setId(entity.getId());
        repository.save(convert(entity));
        return result;
    }

    /** @Description mysql es同步删除数据
    * @Param [id]
    * @return int
    **/
    @Transactional(rollbackFor = Exception.class)
    public int deleteGoods(Integer id){
        repository.deleteById(id);
        return goodsMapper.deleteById(id);
    }

    public SkGoodsEntity getGoods(Integer id){
        return goodsMapper.selectById(id);
    }

    /** @Description mysql es同步更新数据
    * @Param [entity]
    * @return int
    **/
    @Transactional(rollbackFor = Exception.class)
    public int updateGoods(SkGoodsEntity entity){
        repository.deleteById(entity.getId());
        repository.save(convert(entity));
        entity.setLastTime(LocalDateTime.now());
        return goodsMapper.updateById(entity);
    }

    /** @Description 商品减库存
    * @Param [id, num]
    * @return java.lang.Boolean
    **/
    public Boolean reduceStock(Integer id,Integer num){
        SkGoodsEntity skGoodsEntity = goodsMapper.selectById(id);
        Integer stock = skGoodsEntity.getStock();
        if (stock-num<0){
            throw new GlobalException(CodeMsg.NOT_ENOUGH_STOCK);
        }
        skGoodsEntity.setStock(stock-num);
        int update = updateGoods(skGoodsEntity);
        return update > 0;
    }

    private ElasticSearchGoodsEntity convert(SkGoodsEntity entity){
        ElasticSearchGoodsEntity elasticSearchGoodsEntity = new ElasticSearchGoodsEntity();
        BeanUtils.copyProperties(entity,elasticSearchGoodsEntity);
        elasticSearchGoodsEntity.setCreateTime(entity.getCreateTime().toLocalDate());
        elasticSearchGoodsEntity.setLastTime(entity.getLastTime().toLocalDate());

        return elasticSearchGoodsEntity;
    }

}
