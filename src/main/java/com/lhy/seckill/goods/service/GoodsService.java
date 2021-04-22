package com.lhy.seckill.goods.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.lhy.seckill.core.base.MyPage;
import com.lhy.seckill.goods.entity.ElasticSearchGoodsEntity;
import com.lhy.seckill.goods.mapper.GoodsMapper;
import com.lhy.seckill.goods.entity.SkGoodsEntity;
import com.lhy.seckill.goods.mapper.GoodsRepository;
import com.lhy.seckill.goods.service.param.GoodsSearchParam;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @ClassName GoodsService
 * @Description 商品服务
 * @Author lihengyu
 * @Date 2021/2/1 10:24
 * @Version 1.0
 */
@Service
public class GoodsService {

    //TODO 做 es查询 查询前端
    //TODO 优化 图片上传 es同步mysql

    @Autowired(required = false)
    private  GoodsMapper goodsMapper;

    @Autowired
    ElasticsearchRestTemplate template;
    @Autowired
    private GoodsRepository repository;

    /** @Description 构建es分页 分词 高亮查询
    * @Param [param]
    * @return com.lhy.seckill.core.base.MyPage<com.lhy.seckill.goods.entity.SkGoodsEntity>
    **/
    public MyPage<SkGoodsEntity> search(GoodsSearchParam param){
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("name", param.getName()))
                .should(QueryBuilders.matchQuery("description", param.getDescription()));

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(PageRequest.of(param.getCurrent(),param.getSize()))
                .withQuery(queryBuilder)
                .withHighlightFields(
                        new HighlightBuilder.Field("name"),
                        new HighlightBuilder.Field("description"))
                .withHighlightBuilder(new HighlightBuilder().preTags("->").postTags("<-"))
                .build();

        SearchHits<ElasticSearchGoodsEntity> search = template.search(searchQuery, ElasticSearchGoodsEntity.class);
        List<SearchHit<ElasticSearchGoodsEntity>> searchHits = search.getSearchHits();
        ArrayList<ElasticSearchGoodsEntity> goodsEntities = Lists.newArrayListWithCapacity(searchHits.size());
        for (SearchHit<ElasticSearchGoodsEntity> searchHit : searchHits) {
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();

            searchHit.getContent().setName(highlightFields.get("name") == null ?  searchHit.getContent().getName() : highlightFields.get("name").get(0));
            searchHit.getContent().setDescription(highlightFields.get("description") == null ?  searchHit.getContent().getDescription() : highlightFields.get("description").get(0));

            goodsEntities.add(searchHit.getContent());
        }

        //获取es中的数据后根据id去mysql查询详情
        List<SkGoodsEntity> skGoodsEntities = Lists.newArrayListWithCapacity(goodsEntities.size());
        for (ElasticSearchGoodsEntity goodsEntity : goodsEntities) {
            QueryWrapper<SkGoodsEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("id",goodsEntity.getId());
            SkGoodsEntity skGoodsEntity = goodsMapper.selectOne(wrapper);
            skGoodsEntity.setName(goodsEntity.getName());
            skGoodsEntity.setDescription(goodsEntity.getDescription());
            skGoodsEntities.add(skGoodsEntity);
        }

        return new MyPage<SkGoodsEntity>(param.getCurrent(),param.getSize(),searchHits.size(),skGoodsEntities);
    }

    public IPage<SkGoodsEntity> getGoodsPage(int pageNum , int pageSize){
        Page<SkGoodsEntity> page = new Page<>(pageNum,pageSize);
        return goodsMapper.selectPage(page, null);
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


    private ElasticSearchGoodsEntity convert(SkGoodsEntity entity){
        ElasticSearchGoodsEntity elasticSearchGoodsEntity = new ElasticSearchGoodsEntity();
        elasticSearchGoodsEntity.setId(entity.getId());
        elasticSearchGoodsEntity.setName(entity.getName());
        elasticSearchGoodsEntity.setPrice(entity.getPrice());
        elasticSearchGoodsEntity.setStock(entity.getStock());
        elasticSearchGoodsEntity.setDescription(entity.getDescription());
        elasticSearchGoodsEntity.setType(entity.getType());
        elasticSearchGoodsEntity.setCreateTime(entity.getCreateTime().toLocalDate());
        elasticSearchGoodsEntity.setLastTime(entity.getLastTime().toLocalDate());

        return elasticSearchGoodsEntity;
    }

}
